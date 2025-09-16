package com.amar.chat.main_feature.presentation._navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object SplashScreen: Routes()

    @Serializable
    object RegisterScreen: Routes()

    @Serializable
    object HomeScreen: Routes()

    @Serializable
    object UpdateScreen: Routes()

    @Serializable
    object CallsScreen: Routes()

}