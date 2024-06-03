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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.chats
/**
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.ChatIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.stateInUi
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val accountService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    private val firestoreService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService,
): QuickChatViewModel(logService) {
    val chats = firestoreService.userChats
        .stateInUi(emptyList())

    val archives = firestoreService.userArchives
        .stateInUi(emptyList())


    fun saveChatId(chatId: String){
        launchCatching {
            chatIdRepository.saveChatIdState(chatId)
        }
    }
    fun onLongClickChats(chat: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat) {
        launchCatching {
            firestoreService.saveUserArchive(
                uid = accountService.currentUserId,
                chat = chat,
                chatId = chat.chatId
            )
            firestoreService.deleteUserChat(
                uid = accountService.currentUserId,
                chatId = chat.chatId
            )
        }
    }
    fun onLongClickArchives(chat: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat){
        launchCatching {
            firestoreService.saveUserChat(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
            firestoreService.deleteUserArchive(uid = accountService.currentUserId, chatId = chat.chatId)
        }
    }
}

    /**

    private var job: Job? = null

    fun onChatsClick(chat: Chat) {
        launchCatching {
            if (accountService.hasUser) {
                job?.cancel()
                job = viewModelScope.launch {
                    try {
                        val response = chatIdRepository.saveChatIdState(chat.chatId)
                    } catch (e: FirebaseFirestoreException) {
                        println("raheem: ${e.message}")
                    }
                }
            }
        }
    }
}
*/
*/
