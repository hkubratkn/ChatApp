package com.kapirti.ira.ui.presentation.timeline

import android.net.Uri

data class TimelineMediaItem(
    val uri: String,
    val type: TimelineMediaType,
    val timestamp: Long,
    val chatName: String,
    val chatIconUri: Uri?,
)

enum class TimelineMediaType {
    PHOTO,
    VIDEO,
}
