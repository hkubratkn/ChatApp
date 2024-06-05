package com.kapirti.pomodorotechnique_timemanagementmethod.model.service

import com.kapirti.pomodorotechnique_timemanagementmethod.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User


interface FirestoreService {
    suspend fun getUser(userId: String): User?
    suspend fun saveUser(user: User)
    suspend fun saveLang(feedback: Feedback)

    suspend fun updateUserOnline(value: Boolean)
    suspend fun updateUserLastSeen()

}

/**
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Delete
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Report
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos
import kotlinx.coroutines.flow.Flow

    val users: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User>>
    val userChats: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat>>
    val userArchives: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat>>
    val userBlockUsers: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block>>
    val chatMessages: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage>>

    suspend fun getUserPhotos(userId: String): Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>>
    suspend fun getChatUnreadCount(who: String, chatId: String): com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat?

    suspend fun saveUserChat(uid: String, chatId: String, chat: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat)
    suspend fun saveUserArchive(uid: String, chatId: String, chat: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat)
    suspend fun saveChatMessage(chatId: String, chatMessage: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage)
    suspend fun block(uid: String, partnerUid: String, block: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block)
    suspend fun report(uid: String, partnerUid: String, report: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Report)
    suspend fun saveFeedback(feedback: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Feedback)

    suspend fun deleteUserChat(uid: String, chatId: String)
    suspend fun deleteUserArchive(uid: String, chatId: String)
    suspend fun deleteChat(chatId: String)
    suspend fun deleteAccount(delete: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Delete)


    suspend fun updateUserProfilePhoto(photo: String)
    suspend fun updateUserName(newValue: String)
    suspend fun updateUserSurname(newValue: String)
    suspend fun updateUserDisplayName(newValue: String)
    suspend fun updateUserGender(newValue: String)
    suspend fun updateUserDescription(newValue: String)
    suspend fun updateChatLastMessage(who: String, chatId: String, text: String)
    suspend fun updateChatUnreadCount(who: String, chatId: String, count: Int)
    suspend fun updateChatTimestamp(who: String, chatId: String)

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




   /**
    val users: Flow<List<User>>
    val userPhotos: Flow<List<UserPhotos>>
    val chats: Flow<List<Chat>>
    val archiveChats: Flow<List<Chat>>

    suspend fun getUser(uid: String): User?
    suspend fun getUserChat(uid: String, chatId: String): Chat?


    suspend fun saveUserPhotos(userPhotos: UserPhotos)

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
*/
