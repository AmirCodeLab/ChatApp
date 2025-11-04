package com.amar.chat.main_feature.domain.repository

import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.domain.entities.Message
import com.amar.chat.main_feature.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    val currentUserUid: String?
    suspend fun signOut()
    suspend fun createOrUpdateUser(user: User)
    suspend fun getUserByPhone(phone: String): User?
    suspend fun getUser(uid: String): User?
    fun observeChatsForUser(uid: String): Flow<List<ItemChatList>>
    fun observeMessages(chatId: String): Flow<List<Message>>
    suspend fun addContactByPhone(currentUid: String, phone: String): Result<String> // returns chatId
    suspend fun sendMessage(chatId: String, message: Message): Result<Unit>
}