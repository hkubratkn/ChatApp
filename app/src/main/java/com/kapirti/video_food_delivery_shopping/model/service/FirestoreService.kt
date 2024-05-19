/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.video_food_delivery_shopping.model.service

import com.kapirti.video_food_delivery_shopping.model.User
import com.kapirti.video_food_delivery_shopping.model.Chat
import com.kapirti.video_food_delivery_shopping.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface FirestoreService {
    val users: Flow<List<User>>
    val userChats: Flow<List<Chat>>

    suspend fun getUser(userId: String): User?

    suspend fun saveUserChat(uid: String, chatId: String, chat: Chat)
    suspend fun saveChatMessage(chatId: String, chatMessage: ChatMessage)


    /*
    package com.example.makeitso.model.service


    interface StorageService {
        suspend fun getTask(taskId: String): Task?
        suspend fun save(task: Task): String
        suspend fun update(task: Task)
        suspend fun delete(taskId: String)
        suspend fun getCompletedTasksCount(): Int
        suspend fun getImportantCompletedTasksCount(): Int
        suspend fun getMediumHighTasksToCompleteCount(): Int
    }*/




   /** val usersAll: Flow<List<User>>
    val users: Flow<List<User>>
    val userPhotos: Flow<List<UserPhotos>>
    val chats: Flow<List<Chat>>
    val archiveChats: Flow<List<Chat>>

    suspend fun getUser(uid: String): User?
    suspend fun getUserChat(uid: String, chatId: String): Chat?
    fun getChatMessages(chatId: String): Flow<List<Message>>


    suspend fun saveUser(user: User)
    suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat)
    suspend fun saveUserPhotos(userPhotos: UserPhotos)
    suspend fun saveFeedback(feedback: Feedback)
    suspend fun saveLang(feedback: Feedback)
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
*/
   /**
    val userBlock: Flow<List<com.kapirti.ira.model.Block>>


    suspend fun updateUserToken(token: String)
    suspend fun updateUserBirthday(newValue: String)
    suspend fun updateUserChatUnreadZero(chatId: String)
    suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int)


    */
/**
    suspend fun delete(taskId: String)
    suspend fun deleteAllForUser(userId: String)*/
}
