package com.kapirti.ira.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ChatMessage(
    val text: String = "",
   // val isIncoming: Boolean,
    val senderId: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null,
//    val mediaUri: String?,
  //  val mediaMimeType: String?,
)
