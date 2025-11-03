package com.amar.chat.main_feature.domain.entities

data class User(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val about: String = "",
    val imageBase64: String? = null,
)
