package com.zepi.social_chat_food.iraaa.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class UserPhotos(
    val photo: String = "photo",
    @ServerTimestamp
    var date: Timestamp? = null
)
