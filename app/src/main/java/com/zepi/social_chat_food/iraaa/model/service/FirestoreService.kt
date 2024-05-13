package com.zepi.social_chat_food.iraaa.model.service

import com.zepi.social_chat_food.iraaa.model.Block
import com.zepi.social_chat_food.iraaa.model.Chat
import com.zepi.social_chat_food.iraaa.model.ChatRow
import com.zepi.social_chat_food.iraaa.model.Delete
import com.zepi.social_chat_food.iraaa.model.Feedback
import com.zepi.social_chat_food.iraaa.model.Report
import com.zepi.social_chat_food.iraaa.model.User
import com.zepi.social_chat_food.iraaa.model.UserPhotos
import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    val usersAll: Flow<List<User>>
    val users: Flow<List<User>>
    val userPhotos: Flow<List<UserPhotos>>
    val chats: Flow<List<Chat>>
    val archiveChats: Flow<List<Chat>>

    suspend fun getUser(uid: String): User?
    suspend fun getUserChat(uid: String, chatId: String): Chat?
    suspend fun chatRow(chatId: String): Flow<List<ChatRow>>


    suspend fun saveUser(user: User)
    suspend fun saveUserChat(uid: String, chatId: String, chat: Chat)
    suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat)
    suspend fun saveUserPhotos(userPhotos: UserPhotos)
    suspend fun saveChatRow(chatId: String, chatRow: ChatRow)
    suspend fun saveFeedback(feedback: Feedback)
    suspend fun block(uid: String, partnerUid: String, block: Block)
    suspend fun report(uid: String, partnerUid: String, report: Report)

    suspend fun updateUserOnline(value: Boolean)
    suspend fun updateUserLastSeen()
    suspend fun updateUserName(newValue: String)
    suspend fun updateUserSurname(newValue: String)
    suspend fun updateUserDisplayName(newValue: String)
    suspend fun updateUserGender(newValue: String)
    suspend fun updateUserDescription(newValue: String)
    suspend fun updateUserPhoto(photo: String)

    suspend fun deleteUserChat(uid: String, chatId: String)
    suspend fun deleteUserArchive(uid: String, chatId: String)
    suspend fun deleteChat(chatId: String)
    suspend fun deleteAccount(delete: Delete)

   /**
    val userBlock: Flow<List<com.kapirti.ira.model.Block>>

    suspend fun saveLang(feedback: com.kapirti.ira.model.Feedback)


    suspend fun updateUserToken(token: String)
    suspend fun updateUserBirthday(newValue: String)
    suspend fun updateUserChatUnreadZero(chatId: String)
    suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int)


    */
/**
    suspend fun delete(taskId: String)
    suspend fun deleteAllForUser(userId: String)*/
}
