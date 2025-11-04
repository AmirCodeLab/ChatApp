package com.amar.chat.main_feature.domain.entities

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
