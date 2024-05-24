package com.kapirti.ira.soci.data
/**
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zepi.social_chat_food.soci.model.Contact

@Dao
interface ContactDao {

    @Query("SELECT COUNT(id) FROM Contact")
    suspend fun count(): Int

    @Insert
    suspend fun insert(contact: Contact)

    @Query("SELECT * FROM Contact")
    suspend fun loadAll(): List<Contact>
}
*/
