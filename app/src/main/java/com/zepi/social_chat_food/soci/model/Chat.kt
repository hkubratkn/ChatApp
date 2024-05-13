package com.zepi.social_chat_food.soci.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
)

@Entity(
    primaryKeys = ["chatId", "attendeeId"],
    foreignKeys = [
        ForeignKey(
            entity = Chat::class,
            parentColumns = ["id"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["id"],
            childColumns = ["attendeeId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("chatId", "attendeeId", unique = true),
    ],
)
data class ChatAttendee(
    val chatId: Long,
    val attendeeId: Long,
)
