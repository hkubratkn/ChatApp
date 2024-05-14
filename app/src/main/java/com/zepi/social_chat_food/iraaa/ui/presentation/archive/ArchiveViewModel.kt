package com.zepi.social_chat_food.iraaa.ui.presentation.archive

import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.Async
import com.zepi.social_chat_food.iraaa.core.WhileUiSubscribed
import com.zepi.social_chat_food.model.Chat
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel
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
): QChatViewModel(logService) {
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


    private fun handleArchive(archive: List<com.zepi.social_chat_food.model.Chat>): Async<List<com.zepi.social_chat_food.model.Chat>> {
        if (archive == null) {
            return Async.Error(AppText.no_archives_all)
        }
        return Async.Success(archive)
    }

    fun onChatSwipe(chat: com.zepi.social_chat_food.model.Chat){
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
