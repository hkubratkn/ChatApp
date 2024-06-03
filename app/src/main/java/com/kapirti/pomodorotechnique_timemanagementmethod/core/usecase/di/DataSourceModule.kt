package com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.di

import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LocalDataSource
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource =
        localDataSource

}
