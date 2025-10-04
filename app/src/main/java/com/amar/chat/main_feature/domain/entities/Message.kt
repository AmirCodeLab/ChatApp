package com.amar.chat.main_feature.domain.entities

data class Message(
    val message: String,
    val senderPhoneNumber: String = "",
    val receiverPhoneNumber: String = "",
    val timestamp: Long = 0L /*System.currentTimeMillis()*/
)
