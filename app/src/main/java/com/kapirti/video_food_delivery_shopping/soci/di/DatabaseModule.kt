package com.kapirti.video_food_delivery_shopping.soci.di

/**
@Qualifier
annotation class AppCoroutineScope
*/
/**
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zepi.social_chat_food.soci.data.AppDatabase
import com.zepi.social_chat_food.soci.data.ChatDao
import com.zepi.social_chat_food.soci.data.ContactDao
import com.zepi.social_chat_food.soci.data.DatabaseManager
import com.zepi.social_chat_food.soci.data.MessageDao
import com.zepi.social_chat_food.soci.data.RoomDatabaseManager
import com.zepi.social_chat_food.soci.data.populateInitialData
import com.zepi.social_chat_food.soci.widget.model.WidgetModelDao



@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.populateInitialData()
                    }
                },
            ).build()

    @Provides
    fun providesChatDao(database: AppDatabase): ChatDao = database.chatDao()

    @Provides
    fun providesMessageDao(database: AppDatabase): MessageDao = database.messageDao()

    @Provides
    fun providesContactDao(database: AppDatabase): ContactDao = database.contactDao()

    @Provides
    fun providesWidgetModelDao(database: AppDatabase): WidgetModelDao = database.widgetDao()

    @Provides
    @Singleton
    @AppCoroutineScope
    fun providesApplicationCoroutineScope(): CoroutineScope = CoroutineScope(
        Executors.newSingleThreadExecutor().asCoroutineDispatcher(),
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseBindingModule {

    @Binds
    fun bindDatabaseManager(manager: RoomDatabaseManager): DatabaseManager
}*/
