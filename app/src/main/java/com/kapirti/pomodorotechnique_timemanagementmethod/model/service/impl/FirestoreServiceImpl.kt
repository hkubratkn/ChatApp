package com.kapirti.pomodorotechnique_timemanagementmethod.model.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Delete
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Job
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.UserJob
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.trace
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
    private val countryRepository: CountryRepository,
// private val chatIdRepository: ChatIdRepository
): FirestoreService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val jobs: Flow<List<Job>>
        get() =
            countryRepository.readCountryState().flatMapLatest { country ->
                jobCollection(country)
                    .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userChats: Flow<List<Chat>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userChatCollection(user.id)
                    .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userArchives: Flow<List<Chat>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userArchiveCollection(user.id)
                    //                  .whereEqualTo(USER_ID_FIELD, user.id)
//                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    override suspend fun getUser(uid: String): User? = userDocument(uid).get().await().toObject()
    override suspend fun saveUser(user: User): Unit = trace(SAVE_USER_TRACE) { userDocument(auth.currentUserId).set(user).await() }
    override suspend fun saveUserChat(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_CHAT_TRACE) { userChatCollection(uid).document(chatId).set(chat).await() }
    override suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_ARCHIVE_TRACE) { userArchiveCollection(uid).document(chatId).set(chat).await() }
    override suspend fun saveUserJob(userJob: UserJob, id: String): Unit = trace(SAVE_USER_JOB_TRACE){ userJobCollection(auth.currentUserId).document(id).set(userJob).await() }

    override suspend fun saveJob(job: Job, country: String): String = trace(SAVE_JOB_TRACE) { jobCollection(country).add(job).await().id }
    override suspend fun saveFeedback(feedback: Feedback): Unit = trace(SAVE_FEEDBACK_TRACE){ feedbackCollection().add(feedback).await() }
    override suspend fun saveCountry(feedback: Feedback): Unit = trace(SAVE_COUNTRY_TRACE) { countryDocument(feedback).set(feedback).await() }
    override suspend fun updateUserOnline(value: Boolean): Unit = trace(UPDATE_USER_ONLINE_TRACE){ userDocument(auth.currentUserId).update(ONLINE_FIELD, value).await() }
    override suspend fun updateUserLastSeen(): Unit = trace(UPDATE_USER_LAST_SEEN_TRACE) { userDocument(auth.currentUserId).update(LAST_SEEN_FIELD, FieldValue.serverTimestamp()).await()}
    override suspend fun updateUserDisplayName(newValue: String): Unit = trace(UPDATE_USER_DISPLAY_NAME_TRACE){ userDocument(auth.currentUserId).update(DISPLAY_NAME_FIELD, newValue).await()}
    override suspend fun updateUserName(newValue: String): Unit = trace(UPDATE_USER_NAME_TRACE){ userDocument(auth.currentUserId).update(NAME_FIELD, newValue).await() }
    override suspend fun updateUserSurname(newValue: String): Unit = trace(UPDATE_USER_SURNAME_TRACE){ userDocument(auth.currentUserId).update(SURNAME_FIELD, newValue).await() }
    override suspend fun updateUserDescription(newValue: String): Unit = trace(UPDATE_USER_DESCRIPTION_TRACE){ userDocument(auth.currentUserId).update(DESCRIPTION_FIELD, newValue).await() }
    override suspend fun updateUserProfilePhoto(photo: String): Unit = trace(UPDATE_USER_PROFILE_PHOTO_TRACE) { userDocument(auth.currentUserId).update(PHOTO_FIELD, photo).await() }

    override suspend fun deleteAccount(delete: Delete): Unit = trace(DELETE_ACCOUNT_TRACE) { deleteCollection().add(delete).await() }
    override suspend fun deleteUserChat(uid: String, chatId: String) {
        userChatCollection(uid = uid).document(chatId).delete().await()
    }
    override suspend fun deleteUserArchive(uid: String, chatId: String) {
        userArchiveCollection(uid).document(chatId).delete().await()
    }


    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
    private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
    private fun userChatCollection(uid: String): CollectionReference = userDocument(uid).collection(CHAT_COLLECTION)
    private fun userArchiveCollection(uid: String): CollectionReference = userDocument(uid).collection(ARCHIVE_COLLECTION)
    private fun userJobCollection(uid: String): CollectionReference = userDocument(uid).collection(JOB_COLLECTION)


    private fun jobCollection(country: String): CollectionReference = firestore.collection(JOB_COLLECTION).document(country).collection(DOCTOR_COLLECTION)

    private fun countryDocument(feedback: Feedback): DocumentReference = firestore.collection(COUNTRY_COLLECTION).document(feedback.text)
    private fun deleteCollection(): CollectionReference = firestore.collection(DELETE_COLLECTION)
    private fun feedbackCollection(): CollectionReference = firestore.collection(FEEDBACK_COLLECTION)


    companion object {
        private const val USER_COLLECTION = "User"
        private const val CHAT_COLLECTION = "Chat"
        private const val ARCHIVE_COLLECTION = "Archive"
        private const val JOB_COLLECTION = "Job"
        private const val DOCTOR_COLLECTION = "Doctor"
        private const val COUNTRY_COLLECTION = "Country"
        private const val DELETE_COLLECTION = "Delete"
        private const val FEEDBACK_COLLECTION = "Feedback"

        private const val ONLINE_FIELD = "online"
        private const val LAST_SEEN_FIELD = "lastSeen"
        private const val DATE_FIELD = "date"
        private const val DISPLAY_NAME_FIELD = "displayName"
        private const val NAME_FIELD = "name"
        private const val SURNAME_FIELD = "surname"
        private const val DESCRIPTION_FIELD = "description"
        private const val PHOTO_FIELD = "photo"

        private const val SAVE_USER_TRACE = "saveUser"
        private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
        private const val SAVE_USER_ARCHIVE_TRACE = "saveUserArchive"
        private const val SAVE_USER_JOB_TRACE = "saveUserJob"
        private const val SAVE_FEEDBACK_TRACE = "saveFeedback"
        private const val SAVE_COUNTRY_TRACE = "saveCountry"
        private const val SAVE_JOB_TRACE = "saveJob"

        private const val UPDATE_USER_ONLINE_TRACE = "updateUserOnline"
        private const val UPDATE_USER_LAST_SEEN_TRACE = "updateUserLastSeen"
        private const val UPDATE_USER_DISPLAY_NAME_TRACE = "updateUSerDisplayName"
        private const val UPDATE_USER_NAME_TRACE = "updateUserName"
        private const val UPDATE_USER_SURNAME_TRACE = "updateUserSurname"
        private const val UPDATE_USER_DESCRIPTION_TRACE = "updateUserDescription"
        private const val UPDATE_USER_PROFILE_PHOTO_TRACE = "updateUserProfilePhoto"

        private const val DELETE_ACCOUNT_TRACE = "deleteAccount"
    }
}
/**
private const val CHAT_COLLECTION = "Chat"
private const val ARCHIVE_COLLECTION = "Archive"
private const val PHOTOS_COLLECTION = "Photos"
private const val BLOCK_COLLECTION = "Block"
private const val REPORT_COLLECTION = "Report"


private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
private const val SAVE_USER_ARCHIVE_TRACE = "saveUserArchive"
private const val SAVE_USER_PHOTOS_TRACE = "saveUserPhotos"
private const val SAVE_CHAT_ROW = "saveChatRow"
private const val SAVE_BLOCK_USER = "saveBlockUser"
private const val SAVE_REPORT = "saveReport"



override val usersAll: Flow<List<User>>
get() =
auth.currentUser.flatMapLatest { users ->
userCollection().snapshots().map { snapshot -> snapshot.toObjects() }
}

override val users: Flow<List<User>>
get() = langRepository.readLangState().flatMapLatest { langit ->
userCollection().whereEqualTo(LANGUAGE_FIELD, langit).snapshots()
//  .orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots()
.map { snapshot -> snapshot.toObjects() }
}
override val chats: Flow<List<Chat>>
get() =
auth.currentUser.flatMapLatest { user ->
userChatCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
}
override val archiveChats: Flow<List<Chat>>
get() =
auth.currentUser.flatMapLatest { user ->
userArchiveCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
}
override val userPhotos: Flow<List<UserPhotos>>
get() = auth.currentUser.flatMapLatest { user ->
userPhotosCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
}


override suspend fun getUserChat(uid: String, chatId: String): Chat? = chatCollection(uid).document(chatId).get().await().toObject()
override suspend fun chatRow(chatId: String): Flow<List<ChatRow>> = chatCollection(chatId).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
override suspend fun saveUserChat(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_CHAT_TRACE){ userChatCollection(uid).document(chatId).set(chat).await() }
override suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_ARCHIVE_TRACE){ userArchiveCollection(uid).document(chatId).set(chat).await()}
override suspend fun saveUserPhotos(userPhotos: UserPhotos): Unit = trace(SAVE_USER_PHOTOS_TRACE){ userPhotosCollection(auth.currentUserId).add(userPhotos).await() }
override suspend fun saveChatRow(chatId: String, chatRow: ChatRow): Unit = trace(SAVE_CHAT_ROW) { chatCollection(chatId = chatId).add(chatRow).await() }
override suspend fun block(uid: String, partnerUid: String, block: Block): Unit = trace(SAVE_BLOCK_USER) { userBlockDocument(uid, partnerUid).set(block).await() }
override suspend fun report(uid: String, partnerUid: String, report: Report): Unit = trace(SAVE_REPORT) { userReportDocument(uid = uid, partnerUid = partnerUid).set(report).await() }

override suspend fun deleteUserChat(uid: String, chatId: String) { userChatCollection(uid = uid).document(chatId).delete().await() }
override suspend fun deleteUserArchive(uid: String, chatId: String){ userArchiveCollection(uid).document(chatId).delete().await()}

override suspend fun deleteChat(chatId: String) {
val matchingChats = chatCollection(chatId).get().await()
matchingChats.map { it.reference.delete().asDeferred() }.awaitAll()
}



private fun userChatCollection(uid: String): CollectionReference = userDocument(uid).collection(CHAT_COLLECTION)
private fun userArchiveCollection(uid: String): CollectionReference = userDocument(uid).collection(ARCHIVE_COLLECTION)
private fun userPhotosCollection(uid: String): CollectionReference = userDocument(uid).collection(PHOTOS_COLLECTION)
private fun chatCollection(chatId: String): CollectionReference = firestore.collection(CHAT_COLLECTION).document(chatId).collection(chatId)

private fun userBlockCollection(uid: String): CollectionReference = userDocument(uid).collection(BLOCK_COLLECTION)
private fun userBlockDocument(uid: String, partnerUid: String): DocumentReference = userBlockCollection(uid).document(partnerUid)
private fun userReportDocument(uid: String, partnerUid: String): DocumentReference = userDocument(partnerUid).collection(REPORT_COLLECTION).document(uid)




}
}
/**


override val userBlock: Flow<List<com.kapirti.ira.model.Block>>
get() =
auth.currentUser.flatMapLatest { user ->
userBlockCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
}

override suspend fun saveLang(feedback: com.kapirti.ira.model.Feedback): Unit = trace(
SAVE_LANG_TRACE
){ langDocument(feedback).set(feedback).await()}

override suspend fun updateUserToken(token: String): Unit = trace(UPDATE_USER_TOKEN_TRACE) { userDocument(auth.currentUserId).update(
TOKEN_FIELD, token).await() }
override suspend fun updateUserBirthday(newValue: String): Unit = trace(
UPDATE_USER_BIRTHDAY_TRACE
){ userDocument(auth.currentUserId).update(BIRTHDAY_FIELD, newValue).await() }
override suspend fun updateUserChatUnreadZero(chatId: String): Unit = trace(
UPDATE_USER_CHAT_UNREAD_ZERO_TRACE
){ userChatCollection(auth.currentUserId).document(chatId).update(UNREAD_FIELD, 0).await() }
override suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int): Unit = trace(
UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE
){ userChatCollection(uid).document(chatId).update(UNREAD_FIELD, unread).await() }


private fun langDocument(feedback: com.kapirti.ira.model.Feedback): DocumentReference = firestore.collection(
LANG_COLLECTION
).document(feedback.text)


private const val BIRTHDAY_FIELD = "birthday"
private const val DESCRIPTION_FIELD = "description"
private const val PHOTO_FIELD = "photo"
private const val TOKEN_FIELD = "token"
private const val UNREAD_FIELD = "unread"

private const val LANG_COLLECTION = "Lang"

private const val SAVE_LANG_TRACE = "saveLang"

private const val UPDATE_USER_TOKEN_TRACE = "updateUserToken"
private const val UPDATE_USER_BIRTHDAY_TRACE = "updateUserBirthday"
private const val UPDATE_USER_CHAT_UNREAD_ZERO_TRACE = "updateUserChatUnreadZero"
private const val UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE = "updateUserChatUnreadIncrease"

}
/**
override suspend fun delete(taskId: String) {
currentCollection(auth.currentUserId).document(taskId).delete().await()
}

// https://firebase.google.com/docs/firestore/manage-data/delete-data#kotlin+ktx_2
override suspend fun deleteAllForUser(userId: String) {
val matchingTasks = currentCollection(userId).get().await()
matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
}
*/
*/
 *
 * */
/**

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.ChatIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LangRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Delete
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Feedback
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Report
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.asDeferred

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    private val langRepository: LangRepository,
    private val chatIdRepository: ChatIdRepository,
    private val userIdRepository: UserIdRepository,
): com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User>>
        get() =
            userCollection()
                //                  .whereEqualTo(USER_ID_FIELD, user.id)
//                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                .dataObjects()


    @OptIn(ExperimentalCoroutinesApi::class)
    override val userBlockUsers: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userBlockCollection(user.id)
                    .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }


    @OptIn(ExperimentalCoroutinesApi::class)
    override val chatMessages: Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage>>
        get() =
            chatIdRepository.readChatIdState().flatMapLatest { chatId ->
                chatCollection(chatId)
                    .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }




    override suspend fun getUser(uid: String): com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User? = userDocument(uid).get().await().toObject()
    override suspend fun getChatUnreadCount(who: String, chatId: String): com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat? = userChatCollection(who).document(chatId).get().await().toObject()
    override suspend fun getUserPhotos(userUid: String): Flow<List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>> = userPhotosCollection(userUid).snapshots().map { snapshot -> snapshot.toObjects() }
    //get().await().toObjects()



                    //                  .whereEqualTo(USER_ID_FIELD, user.id)
//                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)


    override suspend fun saveUser(user: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(SAVE_USER_TRACE) {
            userDocument(auth.currentUserId).set(user).await()
        }
       override suspend fun saveChatMessage(chatId: String, chatMessage: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            SAVE_CHAT_MESSAGE_TRACE
        ) { chatCollection(chatId = chatId).add(chatMessage).await() }
    override suspend fun block(uid: String, partnerUid: String, block: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Block): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(SAVE_BLOCK_USER) {
            userBlockDocument(
                uid,
                partnerUid
            ).set(block).await()
        }
    override suspend fun report(uid: String, partnerUid: String, report: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Report): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(SAVE_REPORT) {
            userReportDocument(
                uid = uid,
                partnerUid = partnerUid
            ).set(report).await()
        }
    override suspend fun saveFeedback(feedback: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Feedback): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            SAVE_FEEDBACK_TRACE
        ) { feedbackCollection().add(feedback).await() }

    override suspend fun updateUserOnline(value: Boolean): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_ONLINE_TRACE
        ) {
            userDocument(auth.currentUserId).update(ONLINE_FIELD, value).await()
        }
    override suspend fun updateUserLastSeen(): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_LAST_SEEN_TRACE
        ) {
            userDocument(auth.currentUserId).update(LAST_SEEN_FIELD, FieldValue.serverTimestamp())
                .await()
        }


    override suspend fun updateUserDisplayName(newValue: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_DISPLAY_NAME_TRACE
        ) { userDocument(auth.currentUserId).update(DISPLAY_NAME_FIELD, newValue).await() }
    override suspend fun updateUserName(newValue: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_NAME_TRACE
        ) {
            userDocument(auth.currentUserId).update(NAME_FIELD, newValue).await()
        }
    override suspend fun updateUserSurname(newValue: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_SURNAME_TRACE
        ) { userDocument(auth.currentUserId).update(SURNAME_FIELD, newValue).await() }
    override suspend fun updateUserDescription(newValue: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_DESCRIPTION_TRACE
        ) { userDocument(auth.currentUserId).update(DESCRIPTION_FIELD, newValue).await() }

    override suspend fun updateUserGender(newValue: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_USER_GENDER_TRACE
        ) { userDocument(auth.currentUserId).update(GENDER_FIELD, newValue).await() }

    override suspend fun updateChatTimestamp(who: String, chatId: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_CHAT_TIMESTAMP_TRACE
        ) {
            userChatCollection(who).document(chatId)
                .update(DATE_FIELD, FieldValue.serverTimestamp()).await()
        }

    override suspend fun updateChatUnreadCount(who: String, chatId: String, count: Int): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_CHAT_UNREAD_TRACE
        ) {
            userChatCollection(who).document(chatId).update(UNREAD_FIELD, count).await()
        }

    override suspend fun updateChatLastMessage(who: String, chatId: String, text: String): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            UPDATE_CHAT_LAST_MESSAGE_TRACE
        ) {
            userChatCollection(who).document(chatId).update(LAST_MESSAGE_FIELD, text).await()
        }



    override suspend fun deleteChat(chatId: String) {
        val matchingChats = chatCollection(chatId).get().await()
        matchingChats.map { it.reference.delete().asDeferred() }.awaitAll()
    }

    override suspend fun deleteAccount(delete: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Delete): Unit =
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            DELETE_ACCOUNT_TRACE
        ) { deleteCollection().add(delete).await() }


    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
    private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
    private fun userPhotosCollection(uid: String): CollectionReference = userDocument(uid).collection(PHOTOS_COLLECTION)
    private fun chatCollection(chatId: String): CollectionReference = firestore.collection(CHAT_COLLECTION).document(chatId).collection(chatId)
    private fun userBlockCollection(uid: String): CollectionReference = userDocument(uid).collection(BLOCK_COLLECTION)
    private fun userBlockDocument(uid: String, partnerUid: String): DocumentReference = userBlockCollection(uid).document(partnerUid)
    private fun userReportDocument(uid: String, partnerUid: String): DocumentReference = userDocument(partnerUid).collection(REPORT_COLLECTION).document(uid)
    private fun deleteCollection(): CollectionReference = firestore.collection(DELETE_COLLECTION)
    private fun feedbackCollection(): CollectionReference = firestore.collection(FEEDBACK_COLLECTION)



    companion object {
        private const val DATE_FIELD = "date"
        private const val ONLINE_FIELD = "online"
        private const val LAST_SEEN_FIELD = "lastSeen"
        private const val PHOTO_FIELD = "photo"
        private const val DISPLAY_NAME_FIELD = "displayName"
        private const val NAME_FIELD = "name"
        private const val SURNAME_FIELD = "surname"
        private const val GENDER_FIELD = "gender"
        private const val DESCRIPTION_FIELD = "description"
        private const val LAST_MESSAGE_FIELD = "lastMessage"
        private const val UNREAD_FIELD = "unread"

        private const val USER_COLLECTION = "User"
        private const val PHOTOS_COLLECTION = "Photos"
        private const val BLOCK_COLLECTION = "Block"
        private const val REPORT_COLLECTION = "Report"
        private const val DELETE_COLLECTION = "Delete"
        private const val FEEDBACK_COLLECTION = "Feedback"

        private const val SAVE_USER_TRACE = "saveUser"
        private const val SAVE_CHAT_MESSAGE_TRACE = "saveChatMessage"
        private const val SAVE_BLOCK_USER = "saveBlockUser"
        private const val SAVE_REPORT = "saveReport"
        private const val SAVE_FEEDBACK_TRACE = "saveFeedback"

        private const val UPDATE_USER_ONLINE_TRACE = "updateUserOnline"
        private const val UPDATE_USER_LAST_SEEN_TRACE = "updateUserLastSeen"
        private const val UPDATE_USER_PROFILE_PHOTO_TRACE = "updateUserProfilePhoto"
        private const val UPDATE_USER_DISPLAY_NAME_TRACE = "updateUSerDisplayName"
        private const val UPDATE_USER_NAME_TRACE = "updateUserName"
        private const val UPDATE_USER_SURNAME_TRACE = "updateUserSurname"
        private const val UPDATE_USER_GENDER_TRACE = "updateUserGender"
        private const val UPDATE_USER_DESCRIPTION_TRACE = "updateUserDescription"
        private const val UPDATE_CHAT_LAST_MESSAGE_TRACE = "updateChatLastMessage"
        private const val UPDATE_CHAT_UNREAD_TRACE = "updateChatUnread"
        private const val UPDATE_CHAT_TIMESTAMP_TRACE = "updateChatTimestamp"

        private const val DELETE_ACCOUNT_TRACE = "deleteAccount"
    }

}
/**
    private val userCollection get() = firestore.collection(USER_COLLECTION)
    private val userDocument get(uid: String) = userCollection.document(uid)







    override suspend fun getUser(userId: String): User? =
        userDocument.get().await().toObject()




    /*
Copyright 2022 Google LLC


    package com.example.makeitso.model.service.impl


    class StorageServiceImpl @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val auth: AccountService
    ) : StorageService {

        private val collection get() = firestore.collection(TASK_COLLECTION)
            .whereEqualTo(USER_ID_FIELD, auth.currentUserId)

        @OptIn(ExperimentalCoroutinesApi::class)
        override val tasks: Flow<List<Task>>
            get() =
                auth.currentUser.flatMapLatest { user ->
                    firestore
                        .collection(TASK_COLLECTION)
                        .whereEqualTo(USER_ID_FIELD, user.id)
                        .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                        .dataObjects()
                }

        override suspend fun getTask(taskId: String): Task? =
            firestore.collection(TASK_COLLECTION).document(taskId).get().await().toObject()

        override suspend fun save(task: Task): String =
            trace(SAVE_TASK_TRACE) {
                val updatedTask = task.copy(userId = auth.currentUserId)
                firestore.collection(TASK_COLLECTION).add(updatedTask).await().id
            }

        override suspend fun update(task: Task): Unit =
            trace(UPDATE_TASK_TRACE) {
                firestore.collection(TASK_COLLECTION).document(task.id).set(task).await()
            }

        override suspend fun delete(taskId: String) {
            firestore.collection(TASK_COLLECTION).document(taskId).delete().await()
        }

        override suspend fun getCompletedTasksCount(): Int {
            val query = collection.whereEqualTo(COMPLETED_FIELD, true).count()
            return query.get(AggregateSource.SERVER).await().count.toInt()
        }

        override suspend fun getImportantCompletedTasksCount(): Int {
            val query = collection.where(
                Filter.and(
                    Filter.equalTo(COMPLETED_FIELD, true),
                    Filter.or(
                        Filter.equalTo(PRIORITY_FIELD, Priority.High.name),
                        Filter.equalTo(FLAG_FIELD, true)
                    )
                )
            )

            return query.count().get(AggregateSource.SERVER).await().count.toInt()
        }

        override suspend fun getMediumHighTasksToCompleteCount(): Int {
            val query = collection
                .whereEqualTo(COMPLETED_FIELD, false)
                .whereIn(PRIORITY_FIELD, listOf(Priority.Medium.name, Priority.High.name)).count()

            return query.get(AggregateSource.SERVER).await().count.toInt()
        }

        companion object {
            private const val USER_ID_FIELD = "userId"
            private const val COMPLETED_FIELD = "completed"
            private const val PRIORITY_FIELD = "priority"
            private const val FLAG_FIELD = "flag"
            private const val CREATED_AT_FIELD = "createdAt"
            private const val TASK_COLLECTION = "tasks"
            private const val SAVE_TASK_TRACE = "saveTask"
            private const val UPDATE_TASK_TRACE = "updateTask"
        }
    }*/



}/**
    override val usersAll: Flow<List<User>>
        get() =
            auth.currentUser.flatMapLatest { users ->
    userCollection().snapshots().map { snapshot -> snapshot.toObjects() }
            }


    override val users: Flow<List<User>>
        get() = //langRepository.readLangState().flatMapLatest { langit->
            userCollection()
                //.whereEqualTo(LANGUAGE_FIELD, langit)
                .snapshots()
                .map { snapshot -> snapshot.toObjects() }
     //   }

    // langRepository.readLangState().flatMapLatest { langit ->

    //   .whereEqualTo(LANGUAGE_FIELD, langit)

    /**    override val users: Flow<List<User>>
    get() = langRepository.readLangState().flatMapLatest { langit ->
    userCollection().snapshots()
    //userCollection()
    //  .orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots()

    }*/
    override val chats: Flow<List<Chat>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userChatCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .snapshots().map { snapshot -> snapshot.toObjects() }
            }
    override val archiveChats: Flow<List<Chat>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userArchiveCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                    .snapshots().map { snapshot -> snapshot.toObjects() }
            }
    override val userPhotos: Flow<List<UserPhotos>>
        get() = auth.currentUser.flatMapLatest { user ->
            userPhotosCollection(user.id).orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                .snapshots().map { snapshot -> snapshot.toObjects() }
        }



    override suspend fun getUserChat(uid: String, chatId: String): Chat? =
        chatCollection(uid).document(chatId).get().await().toObject()

    override fun getChatMessages(chatId: String): Flow<List<Message>> =
        chatCollection(chatId).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots()
            .map { snapshot -> snapshot.toObjects() }



    override suspend fun saveUserPhotos(userPhotos: com.kapirti.video_food_delivery_shopping.model.UserPhotos): Unit =
        trace(SAVE_USER_PHOTOS_TRACE) {
            userPhotosCollection(auth.currentUserId).add(userPhotos).await()
        }




        private const val LANGUAGE_FIELD = "language"

        private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
        private const val SAVE_USER_PHOTOS_TRACE = "saveUserPhotos"
    }
}
 /**


    override val userBlock: Flow<List<com.kapirti.ira.model.Block>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userBlockCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
            }


   override suspend fun updateUserToken(token: String): Unit = trace(UPDATE_USER_TOKEN_TRACE) { userDocument(auth.currentUserId).update(
        TOKEN_FIELD, token).await() }
       override suspend fun updateUserBirthday(newValue: String): Unit = trace(
        UPDATE_USER_BIRTHDAY_TRACE
    ){ userDocument(auth.currentUserId).update(BIRTHDAY_FIELD, newValue).await() }
  override suspend fun updateUserChatUnreadZero(chatId: String): Unit = trace(
        UPDATE_USER_CHAT_UNREAD_ZERO_TRACE
    ){ userChatCollection(auth.currentUserId).document(chatId).update(UNREAD_FIELD, 0).await() }
    override suspend fun updateUserChatUnreadIncrease(uid: String, chatId: String, unread: Int): Unit = trace(
        UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE
    ){ userChatCollection(uid).document(chatId).update(UNREAD_FIELD, unread).await() }




        private const val BIRTHDAY_FIELD = "birthday"
    private const val DESCRIPTION_FIELD = "description"
    private const val PHOTO_FIELD = "photo"
        private const val TOKEN_FIELD = "token"
        private const val UNREAD_FIELD = "unread"



        private const val UPDATE_USER_TOKEN_TRACE = "updateUserToken"
        private const val UPDATE_USER_BIRTHDAY_TRACE = "updateUserBirthday"
        private const val UPDATE_USER_CHAT_UNREAD_ZERO_TRACE = "updateUserChatUnreadZero"
        private const val UPDATE_USER_CHAT_UNREAD_INCREASE_TRACE = "updateUserChatUnreadIncrease"

    }
    /**
    override suspend fun delete(taskId: String) {
    currentCollection(auth.currentUserId).document(taskId).delete().await()
    }

    // https://firebase.google.com/docs/firestore/manage-data/delete-data#kotlin+ktx_2
    override suspend fun deleteAllForUser(userId: String) {
    val matchingTasks = currentCollection(userId).get().await()
    matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
    }
     */
*/
*/
*/
*/
