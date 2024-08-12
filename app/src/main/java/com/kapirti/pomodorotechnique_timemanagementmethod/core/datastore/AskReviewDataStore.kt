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

package com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class AskReviewDataStore(private val context: Context){

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("userScore")
        val USER_SCORE = intPreferencesKey("user_email")
    }

    fun getScore(): Flow<Int> {
        return context.dataStoree.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val scoreState = preferences[USER_SCORE] ?: 0
                scoreState
            }
    }

    suspend fun increaseScore(){
        context.dataStoree.edit { preferences->
            val currentCounterValue = preferences[USER_SCORE] ?: 0
            preferences[USER_SCORE] = currentCounterValue + 1
        }
    }
}
