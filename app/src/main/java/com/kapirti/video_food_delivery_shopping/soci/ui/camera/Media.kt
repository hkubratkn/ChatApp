package com.kapirti.video_food_delivery_shopping.soci.ui.camera

import android.net.Uri

data class Media(var uri: Uri, var mediaType: MediaType)

enum class MediaType {
    PHOTO,
    VIDEO,
}
