package com.kapirti.ira.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class UserPhotos(
    val photo: String = "photo",
    @ServerTimestamp
    var date: Timestamp? = null
)
