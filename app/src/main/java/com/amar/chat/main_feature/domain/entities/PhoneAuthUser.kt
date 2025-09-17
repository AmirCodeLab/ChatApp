package com.amar.chat.main_feature.domain.entities

data class PhoneAuthUser(
    val userId: String = "",
    val userName: String = "",
    val phoneNumber: String = "",
    val status: String = "",
    val profileImage: String? = null
)
