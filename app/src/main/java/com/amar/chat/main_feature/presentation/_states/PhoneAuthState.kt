package com.amar.chat.main_feature.presentation._states

import com.amar.chat.main_feature.domain.entities.PhoneAuthUser

sealed class PhoneAuthState {
    object Idle : PhoneAuthState()
    object Loading : PhoneAuthState()
    data class OtpSent(val verificationId: String) : PhoneAuthState()
    data class Success(val user: PhoneAuthUser) : PhoneAuthState()
    data class Error(val message: String) : PhoneAuthState()
}