package com.test.test.model.service.impl

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.test.test.model.CallRecord
import com.test.test.model.ChatMessage
import com.test.test.model.ChatRoom
import com.test.test.model.User
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
): FirestoreService {

    override suspend fun saveUser(user: User) {
        userCollection().document(user.id).set(user)
    }

    override suspend fun setUserOnline() {
        userCollection().document(auth.currentUserId).update("status", "online")
    }

    override suspend fun setUserOffline() {
        //userCollection().document(auth.currentUserId).update("status", Timestamp.now().toDate().time.toString())
        userCollection().document(auth.currentUserId).update("status", System.currentTimeMillis().toString())
        //userCollection().document(auth.currentUserId).update("status", Timestamp.now().seconds)
    }

    override suspend fun setUserTyping(typingRoomId: String) {
        userCollection().document(auth.currentUserId).update("typingTo", typingRoomId)
    }

    override suspend fun clearUserTyping(typingRoomId: String) {
        userCollection().document(auth.currentUserId).update("typingTo", "")
    }

    override suspend fun observeOtherChatState(userId: String): Flow<Pair<String, String>> {
        return userCollection().document(userId).snapshots().map { snapshot ->
            snapshot.getString("status").orEmpty() to snapshot.getString("typingTo").orEmpty()
        }

    }

    override suspend fun getUser(userId: String): User? = suspendCoroutine { cont ->

        userCollection().document(userId).get().addOnSuccessListener {
            val x = it.toObject(User::class.java)
            cont.resume(x)
        }
    }

    override suspend fun getChatRoom(chatRoomId: String): ChatRoom? = suspendCoroutine { cont ->
        chatRoomsCollection().document(chatRoomId).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val x = it.result.toObject(ChatRoom::class.java)
                cont.resume(x)
            } else {
                cont.resume(null)
            }
        }
    }

    override suspend fun setChatRoom(chatRoomId: String, chatRoom: ChatRoom) = suspendCoroutine { cont ->
        chatRoomsCollection().document(chatRoomId).set(chatRoom).addOnCompleteListener {
            cont.resume(it.isSuccessful)
        }
    }

    override suspend fun getChatRoomMessageReference(chatRoomId: String): CollectionReference {
        return chatRoomsCollection().document(chatRoomId).collection("chats")
    }

    //@OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getConversations(userId: String): Flow<List<ChatRoom>> {
        return chatRoomsCollection().whereArrayContains("userIds", userId)
            .orderBy("lastMessageTime", Query.Direction.DESCENDING)
            //.addSnapshotListener { value, error -> value.toObjects(ChatRoom::class.java) }
            .snapshotFlow().map { snapshot ->
                android.util.Log.d("myTag","snapshot update triggered..")
                snapshot.toObjects(ChatRoom::class.java)
            }
    }

    override fun updateUserFcmToken(token: String) {
        userCollection().document(auth.currentUserId).update("fcmToken", token)
    }

    override suspend fun saveCallRecord(callRecord: CallRecord) {
        callRecordsCollection().add(callRecord)
    }

    override suspend fun getCallRecords(userId: String): Flow<List<CallRecord>> {
        return callRecordsCollection().whereEqualTo("userId", userId)
            .orderBy("callEnd", Query.Direction.DESCENDING)
            //.addSnapshotListener { value, error -> value.toObjects(ChatRoom::class.java) }
            .snapshotFlow().map { snapshot ->
                snapshot.toObjects(CallRecord::class.java)
            }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<User>>
        get() = auth.currentUser.flatMapLatest { user ->
            userCollection().orderBy(DATE_OF_CREATION_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() } }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentUserConversations: Flow<List<ChatRoom>>
        get() = auth.currentUser.flatMapLatest { user ->
            chatRoomsCollection().whereArrayContains("userIds", user.id).orderBy("lastMessageTime", Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() } }


    override suspend fun getChats(chatRoomId: String): Flow<List<ChatMessage>> {
        return getChatRoomMessageReference(chatRoomId).orderBy("timestamp", Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects(ChatMessage::class.java) }
    }

    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)

    private fun chatRoomsCollection(): CollectionReference = firestore.collection(CHATROOM_COLLECTION)

    private fun callRecordsCollection(): CollectionReference = firestore.collection(CALL_RECORDS_COLLECTION)


    fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
        val listenerRegistration = addSnapshotListener { value, error ->
            if (error != null) {
                android.util.Log.d("myTag2","close it!, error is $error")
                close()
                return@addSnapshotListener
            }
            if (value != null) {
                android.util.Log.d("myTag2","send it!")
                trySend(value)
            }
        }
        awaitClose {
            android.util.Log.d("myTag2","remove it!")
            listenerRegistration.remove()
        }
    }


    companion object {
        private const val CALL_RECORDS_COLLECTION = "CallRecords"
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
