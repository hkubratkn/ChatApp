package com.kapirti.ira.ui.presentation.chats

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.ira.core.datastore.ChatIdRepository
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.soci.ui.stateInUi
import com.kapirti.ira.ui.presentation.ZepiViewModel

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: LogService,
): ZepiViewModel(logService) {
    val chats = firestoreService.userChats
        .stateInUi(emptyList())

    val archives = firestoreService.userArchives
        .stateInUi(emptyList())


    fun saveChatId(chatId: String){
        launchCatching {
            chatIdRepository.saveChatIdState(chatId)
        }
    }
}

    /**

 /**   fun onArchiveSwipe(chat: Chat){
        launchCatching{
            firestoreService.saveUserArchive(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
            firestoreService.deleteUserChat(uid = accountService.currentUserId, chatId = chat.chatId)
        }
    }*/




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
