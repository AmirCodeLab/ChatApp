package com.amar.chat.main_feature.presentation._view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amar.chat.main_feature.domain.entities.Message
import com.amar.chat.main_feature.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repo: FirebaseRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    val currentUserUid: String? = repo.currentUserUid

    fun observeMessages(chatId: String) {
        viewModelScope.launch {
            repo.observeMessages(chatId).collectLatest { list ->
                _messages.value = list
            }
        }
    }

    fun sendMessage(chatId: String, text: String, onComplete: (Boolean, String?) -> Unit) {
        val uid = repo.currentUserUid ?: return onComplete(false, "User not signed in")
        val message = Message(id = "", senderId = uid, text = text, timestamp = System.currentTimeMillis())
        viewModelScope.launch {
            val r = repo.sendMessage(chatId, message)
            if (r.isSuccess) onComplete(true, null) else onComplete(false, r.exceptionOrNull()?.message)
        }
    }

}
