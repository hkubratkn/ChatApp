package com.test.test.model.service.module

import com.test.test.model.service.AccountService
import com.test.test.model.service.ConfigurationService
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.LogService
import com.test.test.model.service.StorageService
import com.test.test.model.service.impl.AccountServiceImpl
import com.test.test.model.service.impl.ConfigurationServiceImpl
import com.test.test.model.service.impl.FirestoreServiceImpl
import com.test.test.model.service.impl.LogServiceImpl
import com.test.test.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideFirestoreService(impl: FirestoreServiceImpl): FirestoreService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}
