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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.chat.chatnope
/**
import com.google.firebase.Timestamp
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.ChatIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.stateInUi
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class ChatNopeViewModel @Inject constructor(
    private val accountService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService,
    private val firestoreService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService,
): QuickChatViewModel(logService) {
    private val _partner = MutableStateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?>(
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User()
    )
    var partner: StateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?> = _partner

    private val _me = MutableStateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?>(
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User()
    )
    var me: StateFlow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?> = _me


    fun getPartnerInfo(partnerId: String) {
        launchCatching {
            _partner.value = firestoreService.getUser(partnerId)
            _me.value = firestoreService.getUser(accountService.currentUserId)
        }
    }


    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input
    private var inputPrefilled = false

    val sendEnabled = _input.map(::isInputValid).stateInUi(false)

    /**
     * We want to update the notification when the corresponding chat screen is open. Setting this
     * to `true` updates the current notification, removing the unread message(s) badge icon and
     * suppressing further notifications.
     */
    fun setForeground(foreground: Boolean) {
        /** val chatId = chatId.value
        if (chatId != 0L) {
        if (foreground) {
        repository.activateChat(chatId)
        } else {
        repository.deactivateChat(chatId)
        }
        }*/
    }


    fun updateInput(input: String) {
        _input.value = input
    }

    fun prefillInput(input: String) {
        if (inputPrefilled) return
        inputPrefilled = true
        updateInput(input)
    }


    /** val chatId = chatId.value
    if (chatId <= 0) return
    val input = _input.value
    if (!isInputValid(input)) return
    viewModelScope.launch {
    repository.sendMessage(chatId, input, null, null)
    _input.value = ""
    }*/


    fun send(
        chatId: String, openAndPopUpChatNopeToExist: () -> Unit,
        partnerName: String, partnerSurname: String, partnerPhoto: String, partnerUid: String,
        profileName: String, profileSurname: String, profilePhoto: String, profileUid: String
    ) {
        launchCatching {
            chatIdRepository.saveChatIdState(chatId)
            val date = Timestamp.now()

            firestoreService.saveChatMessage(
                chatId = chatId,
                com.kapirti.pomodorotechnique_timemanagementmethod.past.model.ChatMessage(
                    text = input.value,
                    senderId = profileUid,
                    date = date,
                )
            )

            firestoreService.saveUserChat(
                uid = profileUid, chatId = chatId,
                com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat(
                    chatId = chatId,
                    partnerName = partnerName,
                    partnerSurname = partnerSurname,
                    partnerPhoto = partnerPhoto,
                    partnerUid = partnerUid,
                    date = date
                )
            )

            firestoreService.saveUserChat(
                uid = partnerUid, chatId = chatId,
                com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat(
                    chatId = chatId,
                    partnerName = profileName,
                    partnerSurname = profileSurname,
                    partnerPhoto = profilePhoto,
                    partnerUid = profileUid,
                    date = date
                )
            )
            openAndPopUpChatNopeToExist()
        }
    }
}


private fun isInputValid(input: String): Boolean = input.isNotBlank()






/**
    val uid = accountService.currentUserId

    private val _text = mutableStateOf<String?>(null)
    val text: String?
        get() = _text.value

    private val _me = MutableStateFlow<User>(User())
    var me: StateFlow<User> = _me

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    fun onTextChange(newValue: String) {
        _text.value = newValue
    }


    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUser(uid)?.let { itUser ->
                _me.value = itUser
                firestoreService.getUser(userUid)?.let { itPartner ->
                    _user.value = itPartner
                }
            }
        }
    }



    }
}
*/
*/
