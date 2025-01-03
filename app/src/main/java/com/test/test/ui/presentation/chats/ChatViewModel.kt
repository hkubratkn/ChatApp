package com.test.test.ui.presentation.chats

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.test.test.common.stateInUi
import com.test.test.model.ChatMessage
import com.test.test.model.ChatRoom
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.impl.FirestoreServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
) : ViewModel() {

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

    fun observeChatMessages(chatId: String) = viewModelScope.launch {
        firestoreService.getChats(chatId).collectLatest {
            uiState.value = uiState.value.copy(messages = it.map { com.test.test.ui.presentation.chats.ChatMessage(text = it.message) })
        }
    }


    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input
    private var inputPrefilled = false

    val sendEnabled = _input.map(::isInputValid).stateInUi(false)

    fun updateInput(input: String) {
        _input.value = input
    }

    fun prefillInput(input: String) {
        if (inputPrefilled) return
        inputPrefilled = true
        updateInput(input)
    }

    fun send() {
        //val chatId = chatId.value
        //if (chatId <= 0) return
        val input = _input.value
        if (!isInputValid(input)) return
        viewModelScope.launch {
            //sendMessage(chatId, input, null, null)
            val myId = "auB1JJ5RUHyZVfD5G9AP" // Hardcoded document id, say I'm name1

            val room = uiState.value.chatRoom
            room?.lastMessageTime = Timestamp.now()
            room?.lastMessageSenderId = myId
            room?.let {
                firestoreService.setChatRoom(room.id, room)

                val chatMessage = ChatMessage(input, myId, Timestamp.now())
                firestoreService.getChatRoomMessageReference(room.id).add(chatMessage).addOnSuccessListener {
                    _input.value = ""
                }
            }
        }
    }

}

private fun isInputValid(input: String): Boolean = input.isNotBlank()
