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

package com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro.di

import android.content.Context
import androidx.room.Room
import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro.WorkTimeDao
import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.pomodoro.WorkTimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkTimeModule {
    @Provides
    fun provideWorkTimeDao(
        workTimeDatabase: WorkTimeDatabase
    ): WorkTimeDao {
        return workTimeDatabase.workTimeDao()
    }

    @Provides
    @Singleton
    fun provideWorkTimeDatabase(
        @ApplicationContext context: Context
    ): WorkTimeDatabase {
        return Room.databaseBuilder(
            context,
            WorkTimeDatabase::class.java,
            "WorkTimeDB"
        ).build()
    }
}
