/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.di

import android.content.Context
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.AskReviewDataStore
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.ChatIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.IsReviewDataStore
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.OnBoardingRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.PomoService
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
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
    fun provideCountryRepository(
        @ApplicationContext context: Context
    ) = CountryRepository(context = context)

    @Provides
    @Singleton
    fun provideEditTypeRepository(
        @ApplicationContext context: Context
    ) = EditTypeRepository(context)

    @Provides
    @Singleton
    fun providePomoRepository(
        @ApplicationContext context: Context
    ) = PomoService(context)

    @Provides
    @Singleton
    fun provideChatIdRepository(
        @ApplicationContext context: Context
    ) = ChatIdRepository(context)

    @Provides
    @Singleton
    fun provideUserIdRepository(
        @ApplicationContext context: Context
    ) = UserIdRepository(context)

    @Provides
    @Singleton
    fun provideAskReviewRepository(
        @ApplicationContext context: Context
    ) = AskReviewDataStore(context)

    @Provides
    @Singleton
    fun provideIsReviewRepository(
        @ApplicationContext context: Context
    ) = IsReviewDataStore(context)
}

