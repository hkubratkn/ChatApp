package com.zepi.social_chat_food.iraaa.ui.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.Async
import com.zepi.social_chat_food.iraaa.core.WhileUiSubscribed
import com.zepi.social_chat_food.iraaa.model.User
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val items: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val firestoreService: FirestoreService,
): QChatViewModel(logService) {

    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _isTaskDeleted = MutableStateFlow(false)
    private val _taskAsync = firestoreService.users
        .map { handleTask(it) }
        .catch { emit(Async.Error(AppText.loading_users_error)) }


    val uiState: StateFlow<HomeUiState> = combine(
        _userMessage, _isLoading, _isTaskDeleted, _taskAsync
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                HomeUiState(isLoading = true)
            }
            is Async.Error -> {
                HomeUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }
            is Async.Success -> {
                HomeUiState(
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
            initialValue = HomeUiState(isLoading = true)
        )


    fun refresh() {
        _isLoading.value = true
        launchCatching {
            _isLoading.value = false
        }
    }

    private fun handleTask(task: List<User>?): Async<List<User>> {
        if (task == null) {
            return Async.Error(AppText.no_users_all)
        }
        return Async.Success(task)
    }
}
