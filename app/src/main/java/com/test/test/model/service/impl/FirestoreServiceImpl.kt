package com.test.test.model.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
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
import com.test.test.model.ChatMessage
import com.test.test.model.ChatRoom
import com.test.test.model.User
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
): FirestoreService {

    override suspend fun saveUser(user: User) {
        userCollection().document(user.id).set(user)
    }

    override suspend fun getUser(userId: String): User? = suspendCoroutine { cont ->

        userCollection().document(userId).get().addOnSuccessListener {
            val x = it.toObject(User::class.java)
            cont.resume(x)
        }
    }

    override suspend fun getChatRoom(chatRoomId: String): ChatRoom? = suspendCoroutine { cont ->
        chatRoomsCollection().document(chatRoomId).get().addOnSuccessListener {
            val x = it.toObject(ChatRoom::class.java)
            cont.resume(x)
        }
    }

    override suspend fun setChatRoom(chatRoomId: String, chatRoom: ChatRoom) {
        chatRoomsCollection().document(chatRoomId).set(chatRoom)
    }

    override suspend fun getChatRoomMessageReference(chatRoomId: String): CollectionReference {
        return chatRoomsCollection().document(chatRoomId).collection("chats")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<User>>
        get() = auth.currentUser.flatMapLatest { user ->
            userCollection().orderBy(DATE_OF_CREATION_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() } }

    override suspend fun getChats(chatRoomId: String): Flow<List<ChatMessage>> {
        return getChatRoomMessageReference(chatRoomId).orderBy("timestamp", Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects(ChatMessage::class.java) }
    }

    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)

    private fun chatRoomsCollection(): CollectionReference = firestore.collection(CHATROOM_COLLECTION)

    companion object {
        private const val CHATROOM_COLLECTION = "ChatRooms"
        private const val USER_COLLECTION = "User"
        private const val DATE_OF_CREATION_FIELD = "dateOfCreation"

        fun getChatRoomId(firstUserId: String, secondUserId: String) : String {
            return if(firstUserId.hashCode() < secondUserId.hashCode()) {
                firstUserId + "_" + secondUserId
            } else {
                secondUserId + "_" + firstUserId
            }
        }
    }
}
