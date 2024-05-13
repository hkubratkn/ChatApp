package com.zepi.social_chat_food.soci.ui.photopicker

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.soci.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val contentResolver: ContentResolver,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val chatIdArg: Long by lazy {
        savedStateHandle.get<Long?>("chatId") ?: throw IllegalArgumentException("chatId is null")
    }

    fun onPhotoPicked(imageUri: Uri) {
        viewModelScope.launch {
            // Ask permission since want to persist media access after app restart too.
            contentResolver.takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            chatRepository.sendMessage(
                chatId = chatIdArg,
                mediaUri = imageUri.toString(),
                mediaMimeType = contentResolver.getType(imageUri),
                text = "",
            )
        }
    }
}
