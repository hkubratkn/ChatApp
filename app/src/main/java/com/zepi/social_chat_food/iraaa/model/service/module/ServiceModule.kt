package com.zepi.social_chat_food.iraaa.model.service.module

import com.zepi.social_chat_food.iraaa.model.service.AccountService
import com.zepi.social_chat_food.iraaa.model.service.ConfigurationService
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.model.service.StorageService
import com.zepi.social_chat_food.iraaa.model.service.impl.AccountServiceImpl
import com.zepi.social_chat_food.iraaa.model.service.impl.ConfigurationServiceImpl
import com.zepi.social_chat_food.iraaa.model.service.impl.FirestoreServiceImpl
import com.zepi.social_chat_food.iraaa.model.service.impl.LogServiceImpl
import com.zepi.social_chat_food.iraaa.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideFirestoreService(impl: FirestoreServiceImpl): FirestoreService

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}
