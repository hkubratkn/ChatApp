package com.zepi.social_chat_food.soci.ui.chat

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
