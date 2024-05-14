package com.zepi.social_chat_food.model

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
