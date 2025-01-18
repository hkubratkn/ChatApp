
package com.test.test.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class ChatMessage(
    @DocumentId val id: String = "id",
    val message: String = "",
    val mediaUri: String = "",
    val mediaMimeType: String = "",
    val senderId: String = "",
    @ServerTimestamp
    var timestamp: Timestamp? = null
)
