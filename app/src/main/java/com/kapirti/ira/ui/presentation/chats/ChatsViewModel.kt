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



/**
import androidx.lifecycle.viewModelScope
import com.kapirti.video_food_delivery_shopping.R.string as AppText
import com.kapirti.video_food_delivery_shopping.core.Async
import com.kapirti.video_food_delivery_shopping.iraaa.core.WhileUiSubscribed
import com.kapirti.video_food_delivery_shopping.model.Chat
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ArchiveUiState(
val items: List<Chat> = emptyList(),
val isLoading: Boolean = false,
val userMessage: Int? = null,
)


@HiltViewModel
class ArchiveViewModel @Inject constructor(
private val accountService: AccountService,
private val firestoreService: FirestoreService,
logService: LogService
): ZepiViewModel(logService) {
private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
private val _isLoading = MutableStateFlow(false)
private val _archiveAsync = firestoreService.archiveChats
.map { handleArchive(it) }
.catch { emit(Async.Error(AppText.loading_archives_error)) }

val uiState: StateFlow<ArchiveUiState> = combine(
_userMessage, _isLoading, _archiveAsync
) { userMessage, isLoading, taskAsync ->
when (taskAsync) {
Async.Loading -> {
ArchiveUiState(isLoading = true)
}
is Async.Error -> {
ArchiveUiState(
userMessage = taskAsync.errorMessage,
)
}
is Async.Success -> {
ArchiveUiState(
items = taskAsync.data,
isLoading = isLoading,
userMessage = userMessage,
)
}
}
}
.stateIn(
scope = viewModelScope,
started = WhileUiSubscribed,
initialValue = ArchiveUiState(isLoading = true)
)


private fun handleArchive(archive: List<Chat>): Async<List<Chat>> {
if (archive == null) {
return Async.Error(AppText.no_archives_all)
}
return Async.Success(archive)
}

fun onChatSwipe(chat: Chat){
launchCatching {
firestoreService.saveUserChat(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
firestoreService.deleteUserArchive(uid = accountService.currentUserId, chatId = chat.chatId)
}
}

fun refresh() {
_isLoading.value = true
launchCatching {
_isLoading.value = false
}
}
}
 */

