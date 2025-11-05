package com.amar.chat.main_feature.domain.entities

data class ItemChatList(
    val chatId: String = "",
    val otherUserId: String = "",
    val otherUserName: String = "",
    val otherUserImageBase64: String? = null,
    val lastMessage: String = "",
    val lastTimestamp: Long = 0L
)