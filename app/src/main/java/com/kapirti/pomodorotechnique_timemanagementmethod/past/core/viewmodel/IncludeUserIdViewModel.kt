package com.kapirti.pomodorotechnique_timemanagementmethod.past.core.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IncludeUserIdViewModel: ViewModel() {
    var partnerId by mutableStateOf<String?>(null)
        private set

    fun addPartnerId(newChat: String) {
        partnerId = newChat
    }
}

