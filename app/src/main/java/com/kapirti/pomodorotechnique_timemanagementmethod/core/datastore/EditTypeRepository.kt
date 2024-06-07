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
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.FEEDBACK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class EditTypeRepository(private val context: Context){
    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "edit_type_pref")
        val EDIT_TYPE_KEY = stringPreferencesKey(name = "edit_type_completed")
    }

    suspend fun saveEditTypeState(type: String) {
        context.dataStore.edit { preferences ->
            preferences[EDIT_TYPE_KEY] = type
        }
    }

    fun readEditTypeState(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val editTypeState = preferences[EDIT_TYPE_KEY] ?: FEEDBACK
                editTypeState
            }
    }
}

