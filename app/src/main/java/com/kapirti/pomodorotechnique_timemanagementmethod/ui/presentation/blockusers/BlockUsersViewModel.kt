package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.blockusers

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.stateInUi
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel

@HiltViewModel
class BlockUsersViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    logService: LogService,
): QuickChatViewModel(logService) {
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
