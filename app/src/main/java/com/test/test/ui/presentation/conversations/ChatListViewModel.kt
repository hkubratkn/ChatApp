/*
 * Copyright (C) 2025 The Android Open Source Project
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

package com.test.test.ui.presentation.conversations

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.ChatRow
import com.test.test.model.service.FirestoreService
import com.test.test.ui.presentation.chats.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ChatListViewModel @Inject constructor(
    //repository: ChatRepository,
    private val firebaseAuth: FirebaseAuth,
    private val firestoreService: FirestoreService,
) : ViewModel() {

    var uiState = mutableStateOf(ConversationsUiState())
        private set

    init {
        observeChatRooms()
    }

    //val chats = listOf<ChatDetail>()



//    val chats = repository
//        .getChats()
//        .stateInUi(emptyList())


    fun observeChatRooms() = viewModelScope.launch {
        val myId = firebaseAuth.currentUser?.uid.orEmpty()
        firestoreService.getConversations(myId).collectLatest { rooms ->
            val rows = rooms.map { room ->
                val otherId = room.userIds.filterNot { it == myId }.first()
                val otherUser = firestoreService.getUser(otherId)!!
                ChatRow(
                    name = otherUser.name,
                    lastMessage = room.lastMessage,
                    profileImage = "",
                    lastTime = (room.lastMessageTime?.seconds?: 0L) * 1000L,
                    userIds = room.userIds
                )
            }
            uiState.value = uiState.value.copy(conversationList = rows)

            //uiState.value = uiState.value.copy(messages = it.map { com.test.test.ui.presentation.chats.ChatMessage(text = it.message, isIncoming = it.senderId != myId) })
        }
    }

}
