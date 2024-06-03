package com.kapirti.pomodorotechnique_timemanagementmethod.soci.data
/**
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zepi.social_chat_food.soci.model.Chat
import com.zepi.social_chat_food.soci.model.ChatAttendee
import com.zepi.social_chat_food.soci.model.Contact
import com.zepi.social_chat_food.soci.model.Message
import com.zepi.social_chat_food.soci.widget.model.WidgetModel
import com.zepi.social_chat_food.soci.widget.model.WidgetModelDao

@Database(
    entities = [
        Contact::class,
        Chat::class,
        ChatAttendee::class,
        Message::class,
        WidgetModel::class,
    ],
    views = [ChatWithLastMessage::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun widgetDao(): WidgetModelDao
}

fun RoomDatabase.wipeAndReinitializeData() = runInTransaction {
    clearAllTables()
    openHelper.writableDatabase.populateInitialData()
}

// Initialization for pre-populating the database
fun SupportSQLiteDatabase.populateInitialData() {
    // Insert self as contact
    insert(
        table = "contact",
        conflictAlgorithm = SQLiteDatabase.CONFLICT_NONE,
        values = ContentValues().apply {
            put("id", 0L)
            put("icon", "you.jpg")
            put("name", "You")
            put("replyModel", "")
        },
    )

    // Populate data for other contacts
    val contacts = Contact.CONTACTS
    val chatIds = contacts.map { it.id }

    contacts.forEachIndexed { index, contact ->
        // Insert contact
        insert(
            table = "Contact",
            conflictAlgorithm = SQLiteDatabase.CONFLICT_IGNORE,
            values = ContentValues().apply {
                put("id", contact.id)
                put("icon", contact.icon)
                put("name", contact.name)
                put("replyModel", contact.replyModel)
            },
        )

        // Insert chat id
        insert(
            table = "Chat",
            conflictAlgorithm = SQLiteDatabase.CONFLICT_IGNORE,
            values = ContentValues().apply {
                put("id", chatIds[index])
            },
        )

        // Insert chat attendee
        insert(
            table = "ChatAttendee",
            conflictAlgorithm = SQLiteDatabase.CONFLICT_IGNORE,
            values = ContentValues().apply {
                put("chatId", chatIds[index])
                put("attendeeId", contact.id)
            },
        )

        val now = System.currentTimeMillis()

        // Add first message
        insert(
            table = "Message",
            conflictAlgorithm = SQLiteDatabase.CONFLICT_NONE,
            values = ContentValues().apply {
                // Use index * 2, since per contact two chats are pre populated
                put("id", (index * 2).toLong())
                put("chatId", chatIds[index])
                put("senderId", contact.id)
                put("text", "Send me a message")
                put("timestamp", now + chatIds[index])
            },
        )

        // Add second message
        insert(
            table = "Message",
            conflictAlgorithm = SQLiteDatabase.CONFLICT_NONE,
            values = ContentValues().apply {
                put("id", (index * 2).toLong() + 1L)
                put("chatId", chatIds[index])
                put("senderId", contact.id)
                put("text", "I will reply in 5 seconds")
                put("timestamp", now + chatIds[index])
            },
        )
    }
}
*/
