package com.kapirti.video_food_delivery_shopping.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Chat(
    val chatId: String = "",
    val unread: Int = 0,
    val partnerName: String = "",
    val partnerSurname: String = "",
    val partnerPhoto: String = "",
    val partnerUid: String = "",
    @ServerTimestamp
    var date: Timestamp? = null
)
