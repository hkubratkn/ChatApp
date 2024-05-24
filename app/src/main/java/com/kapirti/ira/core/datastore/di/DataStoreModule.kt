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

package com.kapirti.ira.core.datastore.di

import android.content.Context
import com.kapirti.ira.core.datastore.ChatIdRepository
import com.kapirti.ira.core.datastore.EditTypeRepository
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.core.datastore.OnBoardingRepository
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
