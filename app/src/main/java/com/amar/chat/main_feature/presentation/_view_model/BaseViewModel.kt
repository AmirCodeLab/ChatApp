package com.amar.chat.main_feature.presentation._view_model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.animation.core.snap
import androidx.lifecycle.ViewModel
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.domain.entities.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream

class BaseViewModel : ViewModel() {

    companion object {
        private const val TAG = "BaseViewModelTAG"
    }

    private val _chatList = MutableStateFlow<List<ItemChatList>>(emptyList())
    val chatList = _chatList.asStateFlow()

    fun searchUserByPhoneNumber(phoneNumber: String, callback: (ItemChatList?) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e(TAG, "searchUserByPhoneNumber: user is not authenticated")
            callback(null)
            return
        }

        val usersReference = FirebaseDatabase.getInstance().getReference("users")
        val query = usersReference.orderByChild("phoneNumber").equalTo(phoneNumber)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.children.first().getValue(ItemChatList::class.java)
                    callback(user)
                    return
                }
                callback(null)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: error fetching users: ${error.message}")
                callback(null)
            }

        })
    }

    fun getChatForUser(userId: String, callback: (List<ItemChatList>) -> Unit) {
        val chatReference = FirebaseDatabase.getInstance().getReference("users/$userId/chats")
        val query = chatReference.orderByChild("userId").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = mutableListOf<ItemChatList>()
                for (chatSnapshot in snapshot.children) {
                    val chat = chatSnapshot.getValue(ItemChatList::class.java)
                    if (chat != null) {
                        chatList.add(chat)
                    }
                }
                callback(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: error fetching users: ${error.message}")
                callback(emptyList())
            }

        })
    }


    init {
        loadChatData()
    }

    private fun loadChatData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e(TAG, "loadChatData: user is not authenticated")
            return
        }

        val userId = currentUser.uid
        val chatReference = FirebaseDatabase.getInstance().getReference("chats")
        val query = chatReference.orderByChild("userId").equalTo(userId)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = mutableListOf<ItemChatList>()
                for (chatSnapshot in snapshot.children) {
                    val chat = chatSnapshot.getValue(ItemChatList::class.java)
                    if (chat != null) {
                        chatList.add(chat)
                    }
                }
                _chatList.value = chatList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: error fetching users: ${error.message}")
            }

        })
    }

    fun loadChatList(currentUserPhoneNumber: String, onChatListLoaded: (List<ItemChatList>) -> Unit) {
        val chatList = mutableListOf<ItemChatList>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats").child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    /*for (chatSnapshot in snapshot.children) {
                        val chat = chatSnapshot.getValue(ItemChatList::class.java)
                        if (chat != null) {
                            chatList.add(chat)
                        }
                    }*/
                    snapshot.children.forEach { child ->
                        val phoneNumber = child.key ?: return@forEach
                        val name = child.child("name").value as? String ?: "Unknown"
                        val image = child.child("image").value as? String

                        //val profileImage = image?.let { decodeBase64ToBitmap(it) }

                        fetchLastMessageForChat(currentUserPhoneNumber, phoneNumber) { lastMessage, timestamp ->
                            chatList.add(
                                ItemChatList(
                                    name = name,
                                    profileImage = image,
                                    message = lastMessage,
                                    time = timestamp
                                )
                            )

                            if (chatList.size == snapshot.childrenCount.toInt()) {
                                onChatListLoaded(chatList)
                            }
                        }
                    }
                } else {
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onChatListLoaded(emptyList())
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })
    }

    fun decodeBase64ToBitmap(base64Image: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (ex: Exception) {
            Log.d(TAG, "decodeBase64ToBitmap: ${ex.message}")
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(decodedBytes)
            BitmapFactory.decodeStream(inputStream)
        } catch (ex: Exception) {
            Log.d(TAG, "decodeBase64ToBitmap: ${ex.message}")
            null
        }
    }

    fun addChat(chat: ItemChatList) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e(TAG, "addChat: user is not authenticated")
            return
        }

        val userId = currentUser.uid
        val chatReference = FirebaseDatabase.getInstance().getReference("chats").push()
        val chatWithUser = chat.copy(userId = userId)
        chatReference.setValue(chatWithUser).addOnSuccessListener {
            Log.d(TAG, "addChat: chat added successfully")
        }.addOnFailureListener {
            Log.e(TAG, "addChat: error adding chat: ${it.message}")
        }
    }

    fun sendMessage(message: String, senderPhoneNumber: String, receiverPhoneNumber: String) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val messageId = databaseReference.push().key ?: return

        val message = Message(
            message = message,
            senderPhoneNumber = senderPhoneNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            timestamp = System.currentTimeMillis()
        )

        databaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        databaseReference.child("messages")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ) {
        val messageRef = FirebaseDatabase.getInstance().getReference("messages")
            .child(senderPhoneNumber).child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    onNewMessage(message)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved: ")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved: ")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ")
            }

        })
    }

    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetch: (String, String) -> Unit
    ) {
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val lastMessage = snapshot.children.first().getValue(Message::class.java)
                        if (lastMessage != null) {
                            onLastMessageFetch(lastMessage.message, lastMessage.timestamp.toString())
                        } else {
                            onLastMessageFetch("--", "--")
                        }
                    } else {
                            onLastMessageFetch("--", "--")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetch("--", "--")
                    Log.d(TAG, "onCancelled: ${error.message}")
                }
            })
    }

}