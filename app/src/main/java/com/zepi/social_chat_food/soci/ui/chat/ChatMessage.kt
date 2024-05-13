package com.zepi.social_chat_food.soci.ui.chat

import android.net.Uri

data class ChatMessage(
    val text: String,
    val mediaUri: String?,
    val mediaMimeType: String?,
    val timestamp: Long,
    val isIncoming: Boolean,
    val senderIconUri: Uri?,
)
