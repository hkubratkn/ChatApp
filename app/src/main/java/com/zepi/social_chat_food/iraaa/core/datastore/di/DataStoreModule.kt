package com.zepi.social_chat_food.iraaa.core.datastore.di

import android.content.Context
import com.zepi.social_chat_food.iraaa.core.datastore.ChatIdRepository
import com.zepi.social_chat_food.iraaa.core.datastore.EditTypeRepository
import com.zepi.social_chat_food.iraaa.core.datastore.LangRepository
import com.zepi.social_chat_food.iraaa.core.datastore.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideOnBoardingRepository(
        @ApplicationContext context: Context
    ) = OnBoardingRepository(context = context)

    @Provides
    @Singleton
    fun provideLangRepository(
        @ApplicationContext context: Context
    ) = LangRepository(context = context)

    @Provides
    @Singleton
    fun provideEditTypeRepository(
        @ApplicationContext context: Context
    ) = EditTypeRepository(context)

    @Provides
    @Singleton
    fun provideChatIdRepository(
        @ApplicationContext context: Context
    ) = ChatIdRepository(context)
}
