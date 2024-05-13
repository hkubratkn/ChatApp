package com.zepi.social_chat_food.iraaa.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Block(
    val uid: String = "",
    val name: String = "",
    val surname: String = "",
    val photo: String = "",
    @ServerTimestamp
    val date: Date? = null
)
