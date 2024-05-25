package com.kapirti.ira.ui.presentation.blockusers

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.soci.ui.stateInUi
import com.kapirti.ira.ui.presentation.ZepiViewModel

@HiltViewModel
class BlockUsersViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    logService: LogService,
): ZepiViewModel(logService) {
    val blockUsers = firestoreService.userBlockUsers.stateInUi(emptyList())
}

/**
    fun onArchiveSwipe(chat: Chat){
        launchCatching{
            firestoreService.saveUserArchive(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
            firestoreService.deleteUserChat(uid = accountService.currentUserId, chatId = chat.chatId)
        }
    }




    private var job: Job? = null

    fun onChatsClick(chat: Chat) {
        launchCatching {
            if (accountService.hasUser) {
                job?.cancel()
                job = viewModelScope.launch {
                    try {
                        val response = chatIdRepository.saveChatIdState(chat.chatId)
                    } catch (e: FirebaseFirestoreException) {
                        println("raheem: ${e.message}")
                    }
                }
            }
        }
    }
}
*/
