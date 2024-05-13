package com.zepi.social_chat_food.soci.ui.home

import androidx.lifecycle.ViewModel
import com.zepi.social_chat_food.soci.repository.ChatRepository
import com.zepi.social_chat_food.soci.ui.stateInUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: ChatRepository,
) : ViewModel() {

    val chats = repository
        .getChats()
        .stateInUi(emptyList())
}
