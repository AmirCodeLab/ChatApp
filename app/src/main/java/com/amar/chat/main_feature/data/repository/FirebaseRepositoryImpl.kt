package com.amar.chat.main_feature.data.repository

import com.amar.chat.main_feature.domain.entities.ChatListEntry
import com.amar.chat.main_feature.domain.entities.ItemChatList
import com.amar.chat.main_feature.domain.entities.Message
import com.amar.chat.main_feature.domain.entities.User
import com.amar.chat.main_feature.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(
    private val auth: FirebaseAuth,
    private val dbRef: DatabaseReference
): FirebaseRepository {

    override val currentUserUid: String? get() = auth.currentUser?.uid

    override suspend fun signOut() = auth.signOut()

    override suspend fun createOrUpdateUser(user: User) {
        dbRef.child("users").child(user.uid).setValue(user).await()
    }

    override suspend fun getUserByPhone(phone: String): User? {
        val query = dbRef.child("users").orderByChild("phone").equalTo(phone)
        val snap = query.get().await()
        for (child in snap.children) {
            return child.getValue(User::class.java)
        }
        return null
    }

    override suspend fun getUser(uid: String): User? {
        val snap = dbRef.child("users").child(uid).get().await()
        return snap.getValue(User::class.java)
    }

    override suspend fun addContactByPhone(currentUid: String, phone: String): Result<String> {
        val found = getUserByPhone(phone) ?: return Result.failure(Exception("User not found"))
        val otherUid = found.uid
        if (otherUid == currentUid) return Result.failure(Exception("Cannot add yourself"))

        val chatId = makeChatId(currentUid, otherUid)

        // Ensure both users have user_chats entries
        val batch = mutableMapOf<String, Any?>()
        batch["user_chats/$currentUid/$chatId"] = ChatListEntry(otherUserId = otherUid, lastMessage = "", lastTimestamp = System.currentTimeMillis())
        batch["user_chats/$otherUid/$chatId"] = ChatListEntry(otherUserId = currentUid, lastMessage = "", lastTimestamp = System.currentTimeMillis())

        dbRef.updateChildren(batch).await()
        return Result.success(chatId)
    }

    override suspend fun sendMessage(chatId: String, message: Message): Result<Unit> {
        val msgRef = dbRef.child("chats").child(chatId).child("messages").push()
        val id = msgRef.key ?: return Result.failure(Exception("Unable to create message id"))
        val msgToSend = message.copy(id = id)
        msgRef.setValue(msgToSend).await()

        // update last message in both user_chats participants
        // retrieve chat participants from chatId
        val parts = chatId.split("_")
        if (parts.size == 2) {
            val uid1 = parts[0]
            val uid2 = parts[1]
            val updates = mapOf(
                "user_chats/$uid1/$chatId/lastMessage" to message.text,
                "user_chats/$uid1/$chatId/lastTimestamp" to message.timestamp,
                "user_chats/$uid2/$chatId/lastMessage" to message.text,
                "user_chats/$uid2/$chatId/lastTimestamp" to message.timestamp,
            )
            dbRef.updateChildren(updates).await()
        }
        return Result.success(Unit)
    }

    override fun observeChatsForUser(uid: String) = callbackFlow {
        val chatsRef = dbRef.child("user_chats").child(uid)
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //val list = mutableListOf<ItemChatList>()
                snapshot.children.forEach { child ->
                    val chatId = child.key ?: return@forEach
                    val entry = child.getValue(ChatListEntry::class.java)
                    if (entry != null) {
                        // For simplicity, we fetch other user's basic info synchronously (could optimize)
                        dbRef.child("users").child(entry.otherUserId).get()
                            .addOnSuccessListener { userSnap ->
                                val user = userSnap.getValue(User::class.java)
                                val item = ItemChatList(
                                    chatId = chatId,
                                    otherUserId = entry.otherUserId,
                                    otherUserName = user?.name ?: "Unknown",
                                    otherUserImageBase64 = user?.imageBase64,
                                    lastMessage = entry.lastMessage ?: "",
                                    lastTimestamp = entry.lastTimestamp ?: 0L
                                )
                                // we don't have an atomic way to push partial updates here; simplest: request full list again
                                // Build list after iterating? for simplicity, just emit current single item appended
                                trySend(listOf(item))
                            }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        chatsRef.addValueEventListener(listener)
        awaitClose { chatsRef.removeEventListener(listener) }
    }

    override fun observeMessages(chatId: String) = callbackFlow {
        val messagesRef = dbRef.child("chats")
            .child(chatId)
            .child("messages")
            //.orderByChild("timestamp")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull { it.getValue(Message::class.java) }
                trySend(messages/*.sortedBy { it.timestamp }*/)
            }
            override fun onCancelled(error: DatabaseError) { close(error.toException()) }
        }
        messagesRef.addValueEventListener(listener)
        awaitClose { messagesRef.removeEventListener(listener) }
    }

    private fun makeChatId(a: String, b: String): String {
        return if (a < b) "${a}_$b" else "${b}_$a"
    }
}