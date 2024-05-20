package com.kapirti.video_food_delivery_shopping.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kapirti.video_food_delivery_shopping.model.Chat

class IncludeChatViewModel: ViewModel() {
    var chat by mutableStateOf<Chat?>(null)
        private set

    fun addChat(newChat: Chat) {
        chat = newChat
    }
}
