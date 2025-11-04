package com.amar.chat.main_feature.domain.entities

data class ChatListEntry(
    val otherUserId: String = "",
    val lastMessage: String? = "",
    val lastTimestamp: Long? = 0L
)