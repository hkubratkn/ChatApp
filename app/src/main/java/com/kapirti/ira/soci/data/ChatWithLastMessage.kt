package com.kapirti.ira.soci.data
/**
import androidx.room.DatabaseView

@DatabaseView(
    """
    SELECT c.id, MAX(m.timestamp) as timestamp, m.text
    FROM Chat as c INNER JOIN Message as m on c.id = m.chatId
    GROUP BY c.id
""",
)
data class ChatWithLastMessage(
val id: Long,
val text: String = "",
val timestamp: Long = -1,
)
*/
