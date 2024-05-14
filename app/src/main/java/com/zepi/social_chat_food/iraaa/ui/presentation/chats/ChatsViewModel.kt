package com.zepi.social_chat_food.iraaa.ui.presentation.chats

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Job
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.Async
import com.zepi.social_chat_food.iraaa.core.WhileUiSubscribed
import com.zepi.social_chat_food.core.datastore.ChatIdRepository
import com.zepi.social_chat_food.model.Chat
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatsUiState(
    val items: List<com.zepi.social_chat_food.model.Chat> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val chatIdRepository: ChatIdRepository,
    logService: LogService,
): QChatViewModel(logService) {
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _isTaskDeleted = MutableStateFlow(false)
    private val _taskAsync = firestoreService.chats
        .map { handleTask(it) }
        .catch { emit(Async.Error(AppText.loading_chats_error)) }


    val uiState: StateFlow<ChatsUiState> = combine(
        _userMessage, _isLoading, _isTaskDeleted, _taskAsync
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                ChatsUiState(isLoading = true)
            }
            is Async.Error -> {
                ChatsUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }
            is Async.Success -> {
                ChatsUiState(
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
            initialValue = ChatsUiState(isLoading = true)
        )


    fun refresh() {
        _isLoading.value = true
        launchCatching {
            _isLoading.value = false
        }
    }

    private fun handleTask(task: List<com.zepi.social_chat_food.model.Chat>?): Async<List<com.zepi.social_chat_food.model.Chat>> {
        if (task == null) {
            return Async.Error(AppText.no_chats_all)
        }
        return Async.Success(task)
    }

    fun onArchiveSwipe(chat: com.zepi.social_chat_food.model.Chat){
        launchCatching{
            firestoreService.saveUserArchive(uid = accountService.currentUserId, chat = chat, chatId = chat.chatId)
            firestoreService.deleteUserChat(uid = accountService.currentUserId, chatId = chat.chatId)
        }
    }




    private var job: Job? = null

    fun onChatsClick(chat: com.zepi.social_chat_food.model.Chat) {
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
