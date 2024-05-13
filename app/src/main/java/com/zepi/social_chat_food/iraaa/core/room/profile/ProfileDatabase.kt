package com.zepi.social_chat_food.iraaa.core.room.profile

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ProfileDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao

}
