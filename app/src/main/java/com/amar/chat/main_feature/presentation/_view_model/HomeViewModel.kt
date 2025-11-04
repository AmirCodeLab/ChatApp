package com.amar.chat.main_feature.presentation._view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: FirebaseRepository
): ViewModel() {

    private val _chatList = MutableStateFlow<List<ItemChatList>>(emptyList())
    val chatList = _chatList.asStateFlow()

    fun observeChatList() {
        val uid = repo.currentUserUid ?: return
        viewModelScope.launch {
            repo.observeChatsForUser(uid).collectLatest { list ->
                _chatList.value = list
            }
        }
    }

    fun addContact(phone: String, onResult: (Boolean, String?) -> Unit) {
        val uid = repo.currentUserUid ?: run {
            onResult(false, "Not signed in")
            return
        }
        viewModelScope.launch {
            val res = repo.addContactByPhone(uid, phone)
            if (res.isSuccess) {
                onResult(true, res.getOrNull())
            } else {
                onResult(false, res.exceptionOrNull()?.message)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch { repo.signOut() }
    }

}
