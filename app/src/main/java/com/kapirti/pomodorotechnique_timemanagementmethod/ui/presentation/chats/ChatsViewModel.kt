package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chats

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.ChatIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.stateInUi
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: LogService,
): QuickChatViewModel(logService) {
    val chats = firestoreService.userChats
        .stateInUi(emptyList())

    val archives = firestoreService.userArchives
        .stateInUi(emptyList())


    fun saveChatId(chatId: String){
        launchCatching {
            chatIdRepository.saveChatIdState(chatId)
        }
    }
    fun onLongClickChats(chat: Chat) {
        launchCatching {
            firestoreService.saveUserArchive(
                uid = accountService.currentUserId,
                chat = chat,
                chatId = chat.chatId
            )
            firestoreService.deleteUserChat(
                uid = accountService.currentUserId,
                chatId = chat.chatId
            )
        }
    }
    fun onLongClickArchives(chat: Chat){
        launchCatching {
            firestoreService.saveUserChat(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
            firestoreService.deleteUserArchive(uid = accountService.currentUserId, chatId = chat.chatId)
        }
    }
}

    /**

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
