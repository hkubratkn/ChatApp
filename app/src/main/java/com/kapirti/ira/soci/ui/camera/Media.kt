package com.kapirti.ira.soci.ui.camera

import android.net.Uri

data class Media(var uri: Uri, var mediaType: MediaType)

enum class MediaType {
    PHOTO,
    VIDEO,
}
