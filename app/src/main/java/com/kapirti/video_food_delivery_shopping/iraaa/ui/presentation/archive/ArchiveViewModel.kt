/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.video_food_delivery_shopping.iraaa.ui.presentation.archive
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
