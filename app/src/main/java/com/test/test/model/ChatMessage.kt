
package com.test.test.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class ChatMessage(
    val message: String = "",
    val senderId: String = "",
    @ServerTimestamp
    var timestamp: Timestamp? = null
)
