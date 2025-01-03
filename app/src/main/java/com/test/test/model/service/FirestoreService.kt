package com.test.test.model.service

import com.test.test.model.ChatRoom
import com.test.test.model.User
import kotlinx.coroutines.flow.Flow


interface FirestoreService {

    suspend fun getUser(userId: String): User?
    val users: Flow<List<User>>

    suspend fun getChatRoom(chatRoomId: String): ChatRoom?

    suspend fun setChatRoom(chatRoomId: String, chatRoom: ChatRoom)
}
