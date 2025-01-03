
package com.test.test.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class ChatRoom(
    @DocumentId val id: String = "id",
    val userIds: List<String> = listOf(),
    @ServerTimestamp
    var lastMessageTime: Timestamp? = null,
    var lastMessageSenderId: String = ""
)
