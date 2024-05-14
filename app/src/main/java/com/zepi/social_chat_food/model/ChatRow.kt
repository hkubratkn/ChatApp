package com.zepi.social_chat_food.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class ChatRow(
    var text: String = "",
    var who: String = "",
    @ServerTimestamp
    var date: Timestamp? = null
)
