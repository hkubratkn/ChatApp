package com.zepi.social_chat_food.iraaa.core.usecase.di

import com.zepi.social_chat_food.iraaa.core.datastore.LocalDataSource
import com.zepi.social_chat_food.iraaa.core.datastore.LocalDataSourceImpl
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
