package com.kapirti.ira.ui.presentation.chat.chatnope

import com.google.firebase.Timestamp
import com.kapirti.ira.core.datastore.ChatIdRepository
import com.kapirti.ira.model.Chat
import com.kapirti.ira.model.ChatMessage
import com.kapirti.ira.model.User
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.soci.ui.stateInUi
import com.kapirti.ira.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class ChatNopeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: LogService,
): ZepiViewModel(logService) {
    private val _partner = MutableStateFlow<User?>(User())
    var partner: StateFlow<User?> = _partner

    private val _me = MutableStateFlow<User?>(User())
    var me: StateFlow<User?> = _me


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
                ChatMessage(
                    text = input.value,
                    senderId = profileUid,
                    date = date,
                )
            )

            firestoreService.saveUserChat(
                uid = profileUid, chatId = chatId,
                Chat(
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
                Chat(
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
