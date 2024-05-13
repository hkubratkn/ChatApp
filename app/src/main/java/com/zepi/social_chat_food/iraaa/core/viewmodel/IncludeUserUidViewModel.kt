package com.zepi.social_chat_food.iraaa.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IncludeUserUidViewModel: ViewModel() {

    var userUid by mutableStateOf<String?>(null)
        private set

    fun addUserUid(newValue: String) {
        userUid = newValue
    }
}
