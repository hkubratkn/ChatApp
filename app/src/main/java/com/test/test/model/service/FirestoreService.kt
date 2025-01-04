package com.test.test.model.service

import com.google.firebase.firestore.CollectionReference
import com.test.test.model.ChatMessage
import com.test.test.model.ChatRoom
import com.test.test.model.User
import kotlinx.coroutines.flow.Flow


interface FirestoreService {

    suspend fun saveUser(user: User)

    suspend fun getUser(userId: String): User?
    val users: Flow<List<User>>

    val currentUserConversations: Flow<List<ChatRoom>>

    suspend fun getChats(chatRoomId: String) : Flow<List<ChatMessage>>

    suspend fun getChatRoom(chatRoomId: String): ChatRoom?

    suspend fun setChatRoom(chatRoomId: String, chatRoom: ChatRoom)

    suspend fun getChatRoomMessageReference(chatRoomId: String) : CollectionReference

    suspend fun getConversations(userId: String) : Flow<List<ChatRoom>>
}
