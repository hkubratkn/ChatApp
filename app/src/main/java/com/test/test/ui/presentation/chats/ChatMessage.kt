package com.test.test.ui.presentation.chats

import android.net.Uri

data class ChatMessage(
    val text: String = "",
    val mediaUri: String? = null,
    val mediaMimeType: String? = null,
    val timestamp: Long = 0L,
    val isIncoming: Boolean = false,
    val senderIconUri: Uri? = null,
)
