package com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.di

import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.UserPreferencesRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.UserPreferencesRepositoryImpl
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
