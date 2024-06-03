package com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Chat

class IncludeChatViewModel: ViewModel() {
    var chat by mutableStateOf<Chat?>(null)
        private set

    fun addChat(newChat: Chat) {
        chat = newChat
    }
}
