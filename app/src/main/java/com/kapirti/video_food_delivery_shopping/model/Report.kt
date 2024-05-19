package com.kapirti.video_food_delivery_shopping.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Report(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val photo: String = "",
    @ServerTimestamp
    val date: Date? = null
)
