package com.kapirti.pomodorotechnique_timemanagementmethod.soci.widget.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.model.Contact

sealed interface WidgetState {
    object Empty : WidgetState
    object Loading : WidgetState
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["id"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("widgetId"),
        Index("contactId"),
    ],
)
data class WidgetModel(
    @PrimaryKey val widgetId: Int,
    val contactId: Long,
    val displayName: String,
    val photo: String,
    val unreadMessages: Boolean = false,
) : WidgetState
