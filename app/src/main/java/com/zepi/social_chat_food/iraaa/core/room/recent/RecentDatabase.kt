package com.zepi.social_chat_food.iraaa.core.room.recent

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recent::class], version = 1, exportSchema = false)
abstract class RecentDatabase: RoomDatabase() {
    abstract fun recentDao(): RecentDao
}
