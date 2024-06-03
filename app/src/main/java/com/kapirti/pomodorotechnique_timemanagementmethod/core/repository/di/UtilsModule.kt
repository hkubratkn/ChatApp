package com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.di

import android.content.Context
import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ) = SettingsRepository(context = context)

}
