package com.test.test.ui.presentation.chats

import com.test.test.model.ChatRoom

data class ChatUiState(
    val chatRoom: ChatRoom? = null,
    val messages: List<ChatMessage> = listOf()
)
