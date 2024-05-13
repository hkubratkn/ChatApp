package com.zepi.social_chat_food.iraaa.core.usecase.di

import com.zepi.social_chat_food.iraaa.core.repository.UserPreferencesRepository
import com.zepi.social_chat_food.iraaa.core.repository.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(repository: UserPreferencesRepositoryImpl): UserPreferencesRepository =
        repository

}
