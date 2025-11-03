package com.amar.chat.main_feature.presentation._states

sealed class PhoneAuthState {
    object Idle : PhoneAuthState()
    object Loading : PhoneAuthState()
    data class OtpSent(val verificationId: String) : PhoneAuthState()
    object Verified : PhoneAuthState()
    data class Error(val message: String) : PhoneAuthState()
}