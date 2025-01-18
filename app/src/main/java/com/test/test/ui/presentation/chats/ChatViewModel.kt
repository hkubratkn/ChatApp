package com.test.test.ui.presentation.chats

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.test.test.BuildConfig
import com.test.test.common.stateInUi
import com.test.test.model.ChatMessage
import com.test.test.model.ChatRoom
import com.test.test.model.User
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.impl.FirestoreServiceImpl
import com.test.test.ui.presentation.notification.NotificationHelper
import com.test.test.ui.presentation.notification.components.ChatState
import com.test.test.ui.presentation.notification.components.FcmApi
import com.test.test.ui.presentation.notification.components.NotificationBody
import com.test.test.ui.presentation.notification.components.SendMessageDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth,
    private val notificationHelper: NotificationHelper
) : ViewModel() {

    //var state by mutableStateOf(ChatState())
    //    private set

    init {
        notificationHelper.setUpNotificationChannels()
    }

    private val isEmulator = Build.HARDWARE.equals("ranchu")

    private val api: FcmApi = Retrofit.Builder()
        .baseUrl(if (isEmulator) BuildConfig.NOTIFICATION_SERVER_EMULATOR else BuildConfig.NOTIFICATION_SERVER_REAL_DEVICE)
        .addConverterFactory(MoshiConverterFactory.create())
        .build().create()

//    fun onRemoteTokenChange(newToken: String) {
//        state = state.copy(
//            remoteToken = newToken
//        )
//    }
//
//    fun onSubmitRemoteToken() {
//        state = state.copy(
//            isEnteringToken = false
//        )
//    }
//
//    fun onMessageChange(message: String) {
//        state = state.copy(
//            messageText = message
//        )
//    }

    fun sendMessageToFCMserver(roomId: String, receiverToken: String, message: ChatMessage, senderName: String) {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                from = roomId,
                to = receiverToken,
                notification = NotificationBody(
                    title = senderName,
                    body = message.message
                )
            )
            try {
                android.util.Log.d("myTag","sending api request now, is emulator : $isEmulator, build hardware : ${Build.HARDWARE}")

                api.sendMessage(messageDto)

                //state = state.copy(messageText = "")
            } catch (e: Exception) {
                android.util.Log.d("myTag","exception here : ${e.message}")
                e.printStackTrace()
            }


        }
    }

    fun sendCallRequestToFCMserver(roomId: String, receiverToken: String, senderName: String) {
        viewModelScope.launch {
            val messageDto = SendMessageDto(
                from = roomId,
                to = receiverToken,
                notification = NotificationBody(
                    title = senderName,
                    body = "Someone is calling..."
                )
            )
            try {
                android.util.Log.d("myTag","sending api request now, is emulator : $isEmulator, build hardware : ${Build.HARDWARE}")

                api.sendCallRequest(messageDto)

                //state = state.copy(messageText = "")
            } catch (e: Exception) {
                android.util.Log.d("myTag","exception here : ${e.message}")
                e.printStackTrace()
            }


        }
    }


    private val chatId = MutableStateFlow("")

    var uiState = mutableStateOf(ChatUiState())
        private set

    fun fetchConversation(firstUserId: String, secondUserId: String) = viewModelScope.launch {
        val chatId = FirestoreServiceImpl.getChatRoomId(firstUserId, secondUserId)
        val chatRoom = firestoreService.getChatRoom(chatId)
        if (chatRoom == null) {
            val newChatRoom = ChatRoom(
                chatId,
                arrayListOf(firstUserId, secondUserId),
                Timestamp.now(),
                ""
            )
            firestoreService.setChatRoom(chatId, newChatRoom)
            uiState.value = uiState.value.copy(chatRoom = newChatRoom)

        } else {
            uiState.value = uiState.value.copy(chatRoom = chatRoom)
        }

        observeChatMessages(chatId)

    }

    fun setChatId(chatId: String) {
        this.chatId.value = chatId
        fetchConversation(chatId)
    }

    fun fetchConversation(roomId: String) = viewModelScope.launch {
        val chatRoom = firestoreService.getChatRoom(roomId)

        val myId = firebaseAuth.currentUser!!.uid
        val otherUserId = chatRoom!!.userIds.filterNot { it == myId }.first()
        val otherUser = firestoreService.getUser(otherUserId)

        uiState.value = uiState.value.copy(chatRoom = chatRoom, otherUserName = otherUser?.name.orEmpty())
//        if (chatRoom == null) {
//            val newChatRoom = ChatRoom(
//                roomId,
//                arrayListOf(firstUserId, secondUserId),
//                Timestamp.now(),
//                ""
//            )
//            firestoreService.setChatRoom(chatId, newChatRoom)
//            uiState.value = uiState.value.copy(chatRoom = newChatRoom)
//
//        } else {
//            uiState.value = uiState.value.copy(chatRoom = chatRoom)
//        }

        observeChatMessages(roomId)

    }

    fun observeChatMessages(chatId: String) = viewModelScope.launch {
        firestoreService.getChats(chatId).collectLatest {
            val myId = firebaseAuth.currentUser?.uid.orEmpty()
            uiState.value = uiState.value.copy(messages = it.map { com.test.test.ui.presentation.chats.ChatMessage(text = it.message, isIncoming = it.senderId != myId) })
        }
    }


    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input
    private var inputPrefilled = false

    val sendEnabled = _input.map(::isInputValid).stateInUi(false)

    fun updateInput(input: String) {
        _input.value = input
    }

    fun sendUriMessage(uriText: String) {
        val chatId = chatId.value
        android.util.Log.d("myTag","try sending uri message : $uriText, chat id : $chatId")
    }

    fun prefillInput(input: String) {
        if (inputPrefilled) return
        inputPrefilled = true
        updateInput(input)
    }

    suspend fun sendMessage(
        chatId: Long,
        text: String,
        mediaUri: String?,
        mediaMimeType: String?,
    ) = viewModelScope.launch {
        //val detail = chatDao.loadDetailById(chatId) ?: return
        // Save the message to the database
        //saveMessageAndNotify(chatId, text, 0L, mediaUri, mediaMimeType, detail, PushReason.OutgoingMessage)

        //val message = detail.firstContact.reply(text).apply { this.chatId = chatId }.build()
        //saveMessageAndNotify(message.chatId, message.text, detail.firstContact.id, message.mediaUri, message.mediaMimeType, detail, PushReason.IncomingMessage)

        // Show notification if the chat is not on the foreground.
//        if (chatId != currentChat) {
//            notificationHelper.showNotification(
//                detail.firstContact,
//                messageDao.loadAll(chatId),
//                false,)
//        }

        //widgetModelRepository.updateUnreadMessagesForContact(contactId = detail.firstContact.id, unread = true)

    }

    fun callFriend() = viewModelScope.launch {
        //sendMessage(chatId, input, null, null)
        val me = firebaseAuth.currentUser
        val myId = me!!.uid

        val room = uiState.value.chatRoom

        val otherUserId = room!!.userIds.filterNot { it == myId }.first()
        val otherUser = firestoreService.getUser(otherUserId)
        otherUser?.let {
            sendCallRequestToFCMserver(room.id, otherUser.fcmToken,  me.email.orEmpty())

        }




        //notificationHelper.showCallNotification(otherUser)
    }

    fun sendPhoto(chatId: Long, photoUri: String) {
        android.util.Log.d("myTag","should send, chat id : $chatId, uri : $photoUri")

    }

    fun send() {
        val chatId = chatId.value
        if (chatId.isEmpty()) return
        val input = _input.value
        if (!isInputValid(input)) return
        viewModelScope.launch {
            //sendMessage(chatId, input, null, null)
            val me = firebaseAuth.currentUser
            val myId = me!!.uid

            val room = uiState.value.chatRoom

            val otherUserId = room!!.userIds.filterNot { it == myId }.first()
            val otherUser = firestoreService.getUser(otherUserId)

            room.lastMessageTime = Timestamp.now()
            room.lastMessageSenderId = myId
            room.lastMessage = input
            room.let { r ->
                firestoreService.setChatRoom(r.id, r)

                val chatMessage = ChatMessage(chatId, input, myId, Timestamp.now())
                firestoreService.getChatRoomMessageReference(r.id).add(chatMessage).addOnSuccessListener {
                    _input.value = ""
//                    notificationHelper.showNotification(
//                        User(name = "notif name"), listOf(ChatMessage(message = input)), true
//                    )

                    //FirebaseMessaging.getInstance().

                    android.util.Log.d("myTag","about to send message to fcm server, my id is : ${myId}")
                    android.util.Log.d("myTag","about to send message to fcm server, the user who has this token should receive the messsage : ${otherUser!!.fcmToken}")
                    sendMessageToFCMserver(r.id, otherUser!!.fcmToken, chatMessage,  me.email.orEmpty())

                }
            }
        }
    }

}

private fun isInputValid(input: String): Boolean = input.isNotBlank()
