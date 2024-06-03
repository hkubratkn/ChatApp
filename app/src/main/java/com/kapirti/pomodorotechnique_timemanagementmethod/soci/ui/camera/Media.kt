package com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.camera

import android.net.Uri

data class Media(var uri: Uri, var mediaType: MediaType)

enum class MediaType {
    PHOTO,
    VIDEO,
}
