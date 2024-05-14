package com.zepi.social_chat_food.iraaa.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zepi.social_chat_food.model.Chat

class IncludeChatViewModel: ViewModel() {
    var chat by mutableStateOf<com.zepi.social_chat_food.model.Chat?>(null)
        private set

    fun addChat(newChat: com.zepi.social_chat_food.model.Chat) {
        chat = newChat
    }
}
