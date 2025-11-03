package com.amar.chat.main_feature.presentation._view_model

import android.app.Activity
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.amar.chat.main_feature.domain.entities.User
import com.amar.chat.main_feature.presentation._states.PhoneAuthState
import com.amar.chat.utils.SharedPref
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class PhoneAuthViewModel(
    private val firebaseAuth: FirebaseAuth,
    private val dataBase: FirebaseDatabase,
    private val sharedPref: SharedPref
) : ViewModel() {

    companion object {
        private const val TAG = "PhoneAuthViewModelTAG"
    }

    private val _authState = MutableStateFlow<PhoneAuthState>(PhoneAuthState.Idle)
    val authState = _authState.asStateFlow()

    fun sendOtp(context: Activity, phoneNumber: String) {
        _authState.value = PhoneAuthState.Loading

        val callBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d(TAG, "onCodeSent: id -- $id")
                Log.d(TAG, "onCodeSent: token -- $token")
                _authState.value = PhoneAuthState.OtpSent(id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: $credential")
            }

            override fun onVerificationFailed(ex: FirebaseException) {
                Log.d(TAG, "onVerificationFailed: ${ex.message}")
                _authState.value = PhoneAuthState.Error(mapFirebaseError(ex))
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context)
            .setCallbacks(callBacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(otp: String, verificationId: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        _authState.value = PhoneAuthState.Loading

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                val uId = user?.uid
                val uNumber = user?.phoneNumber
                sharedPref.isLogin = true
                _authState.value = PhoneAuthState.Verified
            } else {
                _authState.value = PhoneAuthState.Error(task.exception?.message ?: "SigIn Failed")
            }
        }
    }

    fun saveProfile(userId: String, phoneNumber: String, name: String, about: String, image: Bitmap?) {
        val encodeImage = image?.let { convertBitmapToString(it) }
        val user = User(
            uid = userId,
            phone = phoneNumber,
            name = name,
            about = about,
            imageBase64 = encodeImage
        )
        dataBase.reference.child("users").child(userId).setValue(user)
    }

    private fun convertBitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun signOut() {
        firebaseAuth.signOut()
        sharedPref.isLogin = false
    }

    private fun mapFirebaseError(e: FirebaseException): String {
        return when (e) {
            is FirebaseAuthInvalidCredentialsException -> "Invalid credentials"
            is FirebaseTooManyRequestsException -> "Too many requests, try later"
            else -> e.localizedMessage ?: "Unknown error"
        }
    }

}