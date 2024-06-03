package com.kapirti.pomodorotechnique_timemanagementmethod.past.soci.data
/**
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zepi.social_chat_food.soci.model.ChatAttendee
import com.zepi.social_chat_food.soci.model.ChatDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Query("SELECT * FROM ChatWithLastMessage ORDER BY timestamp DESC")
    fun allDetails(): Flow<List<ChatDetail>>

    @Query("SELECT * FROM ChatWithLastMessage WHERE id = :id")
    suspend fun loadDetailById(id: Long): ChatDetail?

    @Query("SELECT * FROM ChatWithLastMessage WHERE id = :id")
    fun detailById(id: Long): Flow<ChatDetail?>

    @Query("SELECT * FROM ChatWithLastMessage")
    suspend fun loadAllDetails(): List<ChatDetail>

    @Query("INSERT INTO Chat (id) VALUES (NULL)")
    suspend fun createChat(): Long

    @Insert
    suspend fun insert(chatAttendee: ChatAttendee): Long

    @Transaction
    suspend fun createDirectChat(contactId: Long): Long {
        val chatId = createChat()
        return insert(ChatAttendee(chatId = chatId, attendeeId = contactId))
    }
}
*/
