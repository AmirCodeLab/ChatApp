package com.amar.chat.main_feature.presentation._navigation

sealed class Routes(val route: String) {
    object SplashScreen : Routes("splash_screen")
    object RegisterScreen : Routes("register_screen")
    object OtpVerificationScreen : Routes("otp_verification_screen")
    object HomeScreen : Routes("home_screen")
    object UpdateScreen : Routes("update_screen")
    object CallsScreen : Routes("call_screen")
}