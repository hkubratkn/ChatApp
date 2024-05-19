package com.kapirti.video_food_delivery_shopping.ui.presentation.chat

import android.net.Uri

data class ChatMessage(
    val text: String,
    val mediaUri: String?,
    val mediaMimeType: String?,
    val timestamp: Long,
    val isIncoming: Boolean,
    val senderIconUri: Uri?,
)
 /**  var who: String = "",
    @ServerTimestamp
    var timestamp: Timestamp? = null*/

