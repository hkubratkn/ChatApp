package com.zepi.social_chat_food.iraaa.ui.presentation.userprofile

import com.zepi.social_chat_food.iraaa.model.User
import com.zepi.social_chat_food.iraaa.model.UserPhotos
import com.zepi.social_chat_food.iraaa.model.service.AccountService
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class UserPhotosUiState(
    val items: List<UserPhotos> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
)


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService,
): QChatViewModel(logService) {
    val hasUser = accountService.hasUser
    val uid = accountService.currentUserId

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    private val _userPhotos = MutableStateFlow<List<UserPhotos>>(emptyList())
    var userPhotos: StateFlow<List<UserPhotos>> = _userPhotos

    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
                firestoreService.userPhotos.collect{ up ->
                    _userPhotos.value = up
                }
            }
        }
    }

/**
    private val _userMessagePhotos: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoadingPhotos = MutableStateFlow(false)
    private val _isTaskDeletedPhotos = MutableStateFlow(false)
    private val _taskAsyncPhotos = firestoreService.userPhotos
        .map { handleUserPhotos(it) }
        .catch { emit(Async.Error(R.string.loading_user_photos_error)) }


    val selectedPhotos: StateFlow<UserPhotosUiState> = combine(
        _userMessagePhotos, _isLoadingPhotos, _isTaskDeletedPhotos, _taskAsyncPhotos
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                UserPhotosUiState(isLoading = true)
            }

            is Async.Error -> {
                UserPhotosUiState(
                    userMessage = taskAsync.errorMessage,
                )
            }

            is Async.Success -> {
                UserPhotosUiState(
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
            initialValue = UserPhotosUiState(isLoading = true)
        )
    private fun handleUserPhotos(task: List<UserPhotos>?): Async<List<UserPhotos>> {
        if (task == null) {
            return Async.Error(R.string.user_photos_not_found)
        }
        return Async.Success(task)
    }
*/



}
