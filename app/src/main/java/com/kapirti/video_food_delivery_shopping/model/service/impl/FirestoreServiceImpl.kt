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

package com.kapirti.video_food_delivery_shopping.model.service.impl




/**
 *
 *
 *
 *
package com.kapirti.ira.model.service.impl

import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.model.*
import com.kapirti.ira.model.service.trace
import kotlinx.coroutines.awaitAll
import javax.inject.Inject


class FirestoreServiceImpl @Inject constructor(
private val firestore: FirebaseFirestore,
private val auth: AccountService,
private val langRepository: LangRepository,
// private val chatIdRepository: ChatIdRepository
): FirestoreService {
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


override suspend fun getUser(uid: String): User? = userDocument(uid).get().await().toObject()
override suspend fun getUserChat(uid: String, chatId: String): Chat? = chatCollection(uid).document(chatId).get().await().toObject()
override suspend fun chatRow(chatId: String): Flow<List<ChatRow>> = chatCollection(chatId).orderBy(DATE_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() }
override suspend fun saveUser(user: User): Unit = trace(SAVE_USER_TRACE) { userDocument(auth.currentUserId).set(user).await() }
override suspend fun saveUserChat(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_CHAT_TRACE){ userChatCollection(uid).document(chatId).set(chat).await() }
override suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat): Unit = trace(SAVE_USER_ARCHIVE_TRACE){ userArchiveCollection(uid).document(chatId).set(chat).await()}
override suspend fun saveUserPhotos(userPhotos: UserPhotos): Unit = trace(SAVE_USER_PHOTOS_TRACE){ userPhotosCollection(auth.currentUserId).add(userPhotos).await() }
override suspend fun saveChatRow(chatId: String, chatRow: ChatRow): Unit = trace(SAVE_CHAT_ROW) { chatCollection(chatId = chatId).add(chatRow).await() }
override suspend fun saveFeedback(feedback: Feedback): Unit = trace(SAVE_FEEDBACK_TRACE){ feedbackCollection().add(feedback).await() }
override suspend fun block(uid: String, partnerUid: String, block: Block): Unit = trace(SAVE_BLOCK_USER) { userBlockDocument(uid, partnerUid).set(block).await() }
override suspend fun report(uid: String, partnerUid: String, report: Report): Unit = trace(SAVE_REPORT) { userReportDocument(uid = uid, partnerUid = partnerUid).set(report).await() }

override suspend fun updateUserOnline(value: Boolean): Unit = trace(UPDATE_USER_ONLINE_TRACE){ userDocument(auth.currentUserId).update(ONLINE_FIELD, value).await() }
override suspend fun updateUserLastSeen(): Unit = trace(UPDATE_USER_LAST_SEEN_TRACE) { userDocument(auth.currentUserId).update(LAST_SEEN_FIELD, FieldValue.serverTimestamp()).await()}
override suspend fun updateUserDisplayName(newValue: String): Unit = trace(UPDATE_USER_DISPLAY_NAME_TRACE){ userDocument(auth.currentUserId).update(DISPLAY_NAME_FIELD, newValue).await()}
override suspend fun updateUserName(newValue: String): Unit = trace(UPDATE_USER_NAME_TRACE){ userDocument(auth.currentUserId).update(NAME_FIELD, newValue).await() }
override suspend fun updateUserSurname(newValue: String): Unit = trace(UPDATE_USER_SURNAME_TRACE){ userDocument(auth.currentUserId).update(SURNAME_FIELD, newValue).await() }
override suspend fun updateUserGender(newValue: String): Unit = trace(UPDATE_USER_GENDER_TRACE){ userDocument(auth.currentUserId).update(GENDER_FIELD, newValue).await() }
override suspend fun updateUserDescription(newValue: String): Unit = trace(UPDATE_USER_DESCRIPTION_TRACE){ userDocument(auth.currentUserId).update(DESCRIPTION_FIELD, newValue).await() }
override suspend fun updateUserPhoto(photo: String): Unit = trace(UPDATE_USER_PHOTO_TRACE) { userDocument(auth.currentUserId).update(PHOTO_FIELD, photo).await() }
override suspend fun deleteAccount(delete: Delete): Unit = trace(DELETE_ACCOUNT_TRACE) { deleteCollection().add(delete).await() }
override suspend fun deleteUserChat(uid: String, chatId: String) { userChatCollection(uid = uid).document(chatId).delete().await() }
override suspend fun deleteUserArchive(uid: String, chatId: String){ userArchiveCollection(uid).document(chatId).delete().await()}

override suspend fun deleteChat(chatId: String) {
val matchingChats = chatCollection(chatId).get().await()
matchingChats.map { it.reference.delete().asDeferred() }.awaitAll()
}




private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
private fun userChatCollection(uid: String): CollectionReference = userDocument(uid).collection(CHAT_COLLECTION)
private fun userArchiveCollection(uid: String): CollectionReference = userDocument(uid).collection(ARCHIVE_COLLECTION)
private fun userPhotosCollection(uid: String): CollectionReference = userDocument(uid).collection(PHOTOS_COLLECTION)
private fun chatCollection(chatId: String): CollectionReference = firestore.collection(CHAT_COLLECTION).document(chatId).collection(chatId)
private fun deleteCollection(): CollectionReference = firestore.collection(DELETE_COLLECTION)
private fun feedbackCollection(): CollectionReference = firestore.collection(FEEDBACK_COLLECTION)
private fun userBlockCollection(uid: String): CollectionReference = userDocument(uid).collection(BLOCK_COLLECTION)
private fun userBlockDocument(uid: String, partnerUid: String): DocumentReference = userBlockCollection(uid).document(partnerUid)
private fun userReportDocument(uid: String, partnerUid: String): DocumentReference = userDocument(partnerUid).collection(REPORT_COLLECTION).document(uid)



companion object {
private const val USER_COLLECTION = "User"
private const val CHAT_COLLECTION = "Chat"
private const val ARCHIVE_COLLECTION = "Archive"
private const val PHOTOS_COLLECTION = "Photos"
private const val DELETE_COLLECTION = "Delete"
private const val FEEDBACK_COLLECTION = "Feedback"
private const val BLOCK_COLLECTION = "Block"
private const val REPORT_COLLECTION = "Report"

private const val DATE_FIELD = "date"
private const val LANGUAGE_FIELD = "language"
private const val ONLINE_FIELD = "online"
private const val LAST_SEEN_FIELD = "lastSeen"
private const val DISPLAY_NAME_FIELD = "displayName"
private const val NAME_FIELD = "name"
private const val SURNAME_FIELD = "surname"
private const val GENDER_FIELD = "gender"
private const val DESCRIPTION_FIELD = "description"
private const val PHOTO_FIELD = "photo"

private const val SAVE_USER_TRACE = "saveUser"
private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
private const val SAVE_USER_ARCHIVE_TRACE = "saveUserArchive"
private const val SAVE_USER_PHOTOS_TRACE = "saveUserPhotos"
private const val SAVE_CHAT_ROW = "saveChatRow"
private const val SAVE_FEEDBACK_TRACE = "saveFeedback"
private const val SAVE_BLOCK_USER = "saveBlockUser"
private const val SAVE_REPORT = "saveReport"
private const val UPDATE_USER_ONLINE_TRACE = "updateUserOnline"
private const val UPDATE_USER_LAST_SEEN_TRACE = "updateUserLastSeen"
private const val UPDATE_USER_DISPLAY_NAME_TRACE = "updateUSerDisplayName"
private const val UPDATE_USER_NAME_TRACE = "updateUserName"
private const val UPDATE_USER_SURNAME_TRACE = "updateUserSurname"
private const val UPDATE_USER_GENDER_TRACE = "updateUserGender"
private const val UPDATE_USER_DESCRIPTION_TRACE = "updateUserDescription"
private const val UPDATE_USER_PHOTO_TRACE = "updateUserPhoto"
private const val DELETE_ACCOUNT_TRACE = "deleteAccount"
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


import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.kapirti.video_food_delivery_shopping.core.datastore.LangRepository
import com.kapirti.video_food_delivery_shopping.model.User
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.model.Chat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
    private val langRepository: LangRepository,
   // private val chatIdRepository: ChatIdRepository
): FirestoreService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<User>>
        get() =
            userCollection()
                //                  .whereEqualTo(USER_ID_FIELD, user.id)
//                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                .dataObjects()



    @OptIn(ExperimentalCoroutinesApi::class)
    override val userChats: Flow<List<Chat>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                userChatCollection(user.id)
                    //                  .whereEqualTo(USER_ID_FIELD, user.id)
//                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }




    override suspend fun getUser(uid: String): User? = userDocument(uid).get().await().toObject()


    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)
    private fun userDocument(uid: String): DocumentReference = userCollection().document(uid)
    private fun userChatCollection(uid: String): CollectionReference = userDocument(uid).collection(CHAT_COLLECTION)





    companion object {
        private const val USER_COLLECTION = "User"
        private const val CHAT_COLLECTION = "Chat"
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

    override suspend fun saveUser(user: User): Unit =
        trace(SAVE_USER_TRACE) { userDocument(auth.currentUserId).set(user).await() }

    override suspend fun saveUserChat(uid: String, chatId: String, chat: Chat): Unit =
        trace(SAVE_USER_CHAT_TRACE) { userChatCollection(uid).document(chatId).set(chat).await() }

    override suspend fun saveUserArchive(uid: String, chatId: String, chat: Chat): Unit =
        trace(SAVE_USER_ARCHIVE_TRACE) {
            userArchiveCollection(uid).document(chatId).set(chat).await()
        }

    override suspend fun saveUserPhotos(userPhotos: com.kapirti.video_food_delivery_shopping.model.UserPhotos): Unit =
        trace(SAVE_USER_PHOTOS_TRACE) {
            userPhotosCollection(auth.currentUserId).add(userPhotos).await()
        }

    override suspend fun saveChatRow(chatId: String, chatRow: Message): Unit =
        trace(SAVE_CHAT_ROW) { chatCollection(chatId = chatId).add(chatRow).await() }

    override suspend fun saveFeedback(feedback: Feedback): Unit =
        trace(SAVE_FEEDBACK_TRACE) { feedbackCollection().add(feedback).await() }

    override suspend fun saveLang(feedback: Feedback): Unit =
        trace(SAVE_LANG_TRACE) { langDocument(feedback).set(feedback).await() }

    override suspend fun block(uid: String, partnerUid: String, block: Block): Unit =
        trace(SAVE_BLOCK_USER) { userBlockDocument(uid, partnerUid).set(block).await() }

    override suspend fun report(uid: String, partnerUid: String, report: Report): Unit =
        trace(SAVE_REPORT) {
            userReportDocument(uid = uid, partnerUid = partnerUid).set(report).await()
        }

    override suspend fun updateUserOnline(value: Boolean): Unit = trace(UPDATE_USER_ONLINE_TRACE) {
        userDocument(auth.currentUserId).update(
            ONLINE_FIELD,
            value
        ).await()
    }

    override suspend fun updateUserLastSeen(): Unit = trace(UPDATE_USER_LAST_SEEN_TRACE) {
        userDocument(auth.currentUserId).update(
            LAST_SEEN_FIELD,
            FieldValue.serverTimestamp()
        ).await()
    }

    override suspend fun updateUserDisplayName(newValue: String): Unit =
        trace(UPDATE_USER_DISPLAY_NAME_TRACE) {
            userDocument(auth.currentUserId).update(
                DISPLAY_NAME_FIELD,
                newValue
            ).await()
        }

    override suspend fun updateUserName(newValue: String): Unit = trace(UPDATE_USER_NAME_TRACE) {
        userDocument(auth.currentUserId).update(
            NAME_FIELD,
            newValue
        ).await()
    }

    override suspend fun updateUserSurname(newValue: String): Unit =
        trace(UPDATE_USER_SURNAME_TRACE) {
            userDocument(auth.currentUserId).update(
                SURNAME_FIELD,
                newValue
            ).await()
        }

    override suspend fun updateUserGender(newValue: String): Unit =
        trace(UPDATE_USER_GENDER_TRACE) {
            userDocument(auth.currentUserId).update(
                GENDER_FIELD,
                newValue
            ).await()
        }

    override suspend fun updateUserDescription(newValue: String): Unit =
        trace(UPDATE_USER_DESCRIPTION_TRACE) {
            userDocument(auth.currentUserId).update(
                DESCRIPTION_FIELD,
                newValue
            ).await()
        }

    override suspend fun updateUserPhoto(photo: String): Unit = trace(UPDATE_USER_PHOTO_TRACE) {
        userDocument(auth.currentUserId).update(PHOTO_FIELD, photo).await()
    }

    override suspend fun deleteAccount(delete: Delete): Unit =
        trace(DELETE_ACCOUNT_TRACE) { deleteCollection().add(delete).await() }

    override suspend fun deleteUserChat(uid: String, chatId: String) {
        userChatCollection(uid = uid).document(chatId).delete().await()
    }

    override suspend fun deleteUserArchive(uid: String, chatId: String) {
        userArchiveCollection(uid).document(chatId).delete().await()
    }

    override suspend fun deleteChat(chatId: String) {
        val matchingChats = chatCollection(chatId).get().await()
        matchingChats.map { it.reference.delete().asDeferred() }.awaitAll()
    }




    private fun userArchiveCollection(uid: String): CollectionReference =
        userDocument(uid).collection(ARCHIVE_COLLECTION)

    private fun userPhotosCollection(uid: String): CollectionReference =
        userDocument(uid).collection(PHOTOS_COLLECTION)

    private fun chatCollection(chatId: String): CollectionReference =
        firestore.collection(CHAT_COLLECTION).document(chatId).collection(chatId)

    private fun deleteCollection(): CollectionReference = firestore.collection(DELETE_COLLECTION)
    private fun feedbackCollection(): CollectionReference =
        firestore.collection(FEEDBACK_COLLECTION)

    private fun langDocument(feedback: Feedback): DocumentReference =
        firestore.collection(LANG_COLLECTION).document(feedback.text)

    private fun userBlockCollection(uid: String): CollectionReference =
        userDocument(uid).collection(BLOCK_COLLECTION)

    private fun userBlockDocument(uid: String, partnerUid: String): DocumentReference =
        userBlockCollection(uid).document(partnerUid)

    private fun userReportDocument(uid: String, partnerUid: String): DocumentReference =
        userDocument(partnerUid).collection(REPORT_COLLECTION).document(uid)


    companion object {
        private const val ARCHIVE_COLLECTION = "Archive"
        private const val PHOTOS_COLLECTION = "Photos"
        private const val DELETE_COLLECTION = "Delete"
        private const val FEEDBACK_COLLECTION = "Feedback"
        private const val LANG_COLLECTION = "Lang"
        private const val BLOCK_COLLECTION = "Block"
        private const val REPORT_COLLECTION = "Report"

        private const val DATE_FIELD = "date"
        private const val LANGUAGE_FIELD = "language"
        private const val ONLINE_FIELD = "online"
        private const val LAST_SEEN_FIELD = "lastSeen"
        private const val DISPLAY_NAME_FIELD = "displayName"
        private const val NAME_FIELD = "name"
        private const val SURNAME_FIELD = "surname"
        private const val GENDER_FIELD = "gender"
        private const val DESCRIPTION_FIELD = "description"
        private const val PHOTO_FIELD = "photo"

        private const val SAVE_USER_TRACE = "saveUser"
        private const val SAVE_USER_CHAT_TRACE = "saveUserChat"
        private const val SAVE_USER_ARCHIVE_TRACE = "saveUserArchive"
        private const val SAVE_USER_PHOTOS_TRACE = "saveUserPhotos"
        private const val SAVE_CHAT_ROW = "saveChatRow"
        private const val SAVE_FEEDBACK_TRACE = "saveFeedback"
        private const val SAVE_LANG_TRACE = "saveLang"
        private const val SAVE_BLOCK_USER = "saveBlockUser"
        private const val SAVE_REPORT = "saveReport"
        private const val UPDATE_USER_ONLINE_TRACE = "updateUserOnline"
        private const val UPDATE_USER_LAST_SEEN_TRACE = "updateUserLastSeen"
        private const val UPDATE_USER_DISPLAY_NAME_TRACE = "updateUSerDisplayName"
        private const val UPDATE_USER_NAME_TRACE = "updateUserName"
        private const val UPDATE_USER_SURNAME_TRACE = "updateUserSurname"
        private const val UPDATE_USER_GENDER_TRACE = "updateUserGender"
        private const val UPDATE_USER_DESCRIPTION_TRACE = "updateUserDescription"
        private const val UPDATE_USER_PHOTO_TRACE = "updateUserPhoto"
        private const val DELETE_ACCOUNT_TRACE = "deleteAccount"
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
