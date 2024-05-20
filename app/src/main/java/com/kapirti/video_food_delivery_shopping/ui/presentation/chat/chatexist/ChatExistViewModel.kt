package com.kapirti.video_food_delivery_shopping.ui.presentation.chat.chatexist

import androidx.compose.runtime.mutableStateOf
import com.kapirti.video_food_delivery_shopping.model.Chat
import com.kapirti.video_food_delivery_shopping.model.User
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.soci.ui.stateInUi
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@HiltViewModel
class ChatExistViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
// private val repository: ChatRepository,
    logService: LogService
) : ZepiViewModel(logService) {
    var uiState = mutableStateOf(ChatExistUiState())
        private set

    private val chatId
        get() = uiState.value.chatId
    private val partnerId
        get() = uiState.value.partnerUid





    private val _partner = MutableStateFlow<User?>(User())
    var partner: StateFlow<User?> = _partner

    private val _me = MutableStateFlow<User?>(User())
    var me: StateFlow<User?> = _me



    fun initialize(chat: Chat){
        launchCatching {
            onChatIdChange(chat.chatId)
            onPartnerIdChange(chat.partnerUid)
            getPartnerInfo()
        }
    }
    private fun getPartnerInfo() {
        launchCatching {
            _partner.value = firestoreService.getUser(partnerId)
            _me.value = firestoreService.getUser(accountService.currentUserId)
        }
    }

    private fun onChatIdChange(newValue: String) {
        uiState.value = uiState.value.copy(chatId = newValue)
    }
    private fun onPartnerIdChange(newValue: String) {
        uiState.value = uiState.value.copy(partnerUid = newValue)
    }

/**
    private val chatId = MutableStateFlow(0L)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _chat = chatId.flatMapLatest { id -> repository.findChat(id) }

    private val attendees = _chat.map { c -> (c?.attendees ?: emptyList()).associateBy { it.id } }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _messages = chatId.flatMapLatest { id -> repository.findMessages(id) }

    val chat = _chat.stateInUi(null)

    val messages = combine(_messages, attendees) { messages, attendees ->
// Build a list of `ChatMessage` from this list of `Message`.
        buildList {
            for (i in messages.indices) {
                val message = messages[i]
// Show the contact icon only at the first message if the same sender has multiple
// messages in a row.
                val showIcon = i + 1 >= messages.size ||
                    messages[i + 1].senderId != message.senderId
                val iconUri = if (showIcon) attendees[message.senderId]?.iconUri else null
                add(
                    ChatMessage(
                        text = message.text,
                        mediaUri = message.mediaUri,
                        mediaMimeType = message.mediaMimeType,
                        timestamp = message.timestamp,
                        isIncoming = message.isIncoming,
                        senderIconUri = iconUri,
                    ),
                )
            }
        }
    }.stateInUi(emptyList())*/

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
        val chatId = chatId
        if (chatId != "") {
            if (foreground) {
               // repository.activateChat(chatId)
            } else {
               // repository.deactivateChat(chatId)
            }
        }
    }

    fun updateInput(input: String) {
        _input.value = input
    }

    fun prefillInput(input: String) {
        if (inputPrefilled) return
        inputPrefilled = true
        updateInput(input)
    }

    fun send() {
        val chatId = chatId.toInt()
        if (chatId <= 0) return
        val input = _input.value
        if (!isInputValid(input)) return
        launchCatching {
           // repository.sendMessage(chatId, input, null, null)
            _input.value = ""
        }
    }
}

private fun isInputValid(input: String): Boolean = input.isNotBlank()






/**

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.chatext.repository.ChatRepository
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.soci.ui.stateInUi
import com.zepi.social_chat_food.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
private val repository: ChatRepository,
logService: LogService
) : ZepiViewModel(logService) {
private val chatId = MutableStateFlow("0L")

@OptIn(ExperimentalCoroutinesApi::class)
private val _messages = chatId.flatMapLatest { id -> repository.findMessages(id) }

private val attendees = _chat.map { c -> (c?.attendees ?: emptyList()).associateBy { it.id } }

// val messages = combine(_messages, attendees) { messages, attendees ->
val messages = combine(_messages){ messages ->
// Build a list of `ChatMessage` from this list of `Message`.
buildList {
for (i in messages.indices) {
val message = messages[i]
// Show the contact icon only at the first message if the same sender has multiple
// messages in a row.
val showIcon = i + 1 >= messages.size ||
messages[i + 1].senderId != message.senderId
val iconUri = if (showIcon) attendees[message.senderId]?.iconUri else null
add(
ChatMessage(
text = message.text,
mediaUri = message.mediaUri,
mediaMimeType = message.mediaMimeType,
timestamp = message.timestamp,
isIncoming = message.isIncoming,
senderIconUri = iconUri,
),
)
}
}
}.stateInUi(emptyList())


private val _input = MutableStateFlow("")
val input: StateFlow<String> = _input
private var inputPrefilled = false

val sendEnabled = _input.map(::isInputValid).stateInUi(false)


fun setChatId(chatId: String) {
this.chatId.value = chatId
}
fun prefillInput(input: String) {
if (inputPrefilled) return
inputPrefilled = true
updateInput(input)
}
fun updateInput(input: String) {
_input.value = input
}
}

private fun isInputValid(input: String): Boolean = input.isNotBlank()
/**
private val userId = MutableStateFlow("id")

@OptIn(ExperimentalCoroutinesApi::class)
private val _chat = chatId.flatMapLatest { id -> repository.findChat(id) }


@OptIn(ExperimentalCoroutinesApi::class)
private val _user = userId.flatMapLatest { id -> repository.findUser(id) }


val chat = _chat.stateInUi(null)
val user = _user.stateInUi(null)
*/

/**
 *
 *
 *
 *
private val _user = MutableStateFlow<User>(User())
var user: StateFlow<User> = _user

private val _userPhotos = MutableStateFlow<List<UserPhotos>>(emptyList())
var userPhotos: StateFlow<List<UserPhotos>> = _userPhotos

fun initialize(userUid: String) {
launchCatching {
firestoreService.getUser(userUid)?.let { itUser ->
_user.value = itUser
firestoreService.userPhotos.collect{ up ->
_userPhotos.value = up
}
}
}
}












/**
 * We want to update the notification when the corresponding chat screen is open. Setting this
 * to `true` updates the current notification, removing the unread message(s) badge icon and
 * suppressing further notifications.
*/
fun setForeground(foreground: Boolean) {
val chatId = chatId.value
if (chatId != 0L) {
if (foreground) {
repository.activateChat(chatId)
} else {
repository.deactivateChat(chatId)
}
}
}






fun send() {
val chatId = chatId.value
if (chatId <= 0) return
val input = _input.value
if (!isInputValid(input)) return
viewModelScope.launch {
repository.sendMessage(chatId, input, null, null)
_input.value = ""
}
}
}

*/





/**
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.soci.repository.ChatRepository
import com.zepi.social_chat_food.soci.ui.stateInUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
private val repository: ChatRepository,
) : ViewModel() {

private val chatId = MutableStateFlow(0L)

@OptIn(ExperimentalCoroutinesApi::class)
private val _chat = chatId.flatMapLatest { id -> repository.findChat(id) }

private val attendees = _chat.map { c -> (c?.attendees ?: emptyList()).associateBy { it.id } }

@OptIn(ExperimentalCoroutinesApi::class)
private val _messages = chatId.flatMapLatest { id -> repository.findMessages(id) }

val chat = _chat.stateInUi(null)

val messages = combine(_messages, attendees) { messages, attendees ->
// Build a list of `ChatMessage` from this list of `Message`.
buildList {
for (i in messages.indices) {
val message = messages[i]
// Show the contact icon only at the first message if the same sender has multiple
// messages in a row.
val showIcon = i + 1 >= messages.size ||
messages[i + 1].senderId != message.senderId
val iconUri = if (showIcon) attendees[message.senderId]?.iconUri else null
add(
ChatMessage(
text = message.text,
mediaUri = message.mediaUri,
mediaMimeType = message.mediaMimeType,
timestamp = message.timestamp,
isIncoming = message.isIncoming,
senderIconUri = iconUri,
),
)
}
}
}.stateInUi(emptyList())

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
val chatId = chatId.value
if (chatId != 0L) {
if (foreground) {
repository.activateChat(chatId)
} else {
repository.deactivateChat(chatId)
}
}
}

fun setChatId(chatId: Long) {
this.chatId.value = chatId
}

fun updateInput(input: String) {
_input.value = input
}

fun prefillInput(input: String) {
if (inputPrefilled) return
inputPrefilled = true
updateInput(input)
}

fun send() {
val chatId = chatId.value
if (chatId <= 0) return
val input = _input.value
if (!isInputValid(input)) return
viewModelScope.launch {
repository.sendMessage(chatId, input, null, null)
_input.value = ""
}
}
}

private fun isInputValid(input: String): Boolean = input.isNotBlank()
*/
 */
