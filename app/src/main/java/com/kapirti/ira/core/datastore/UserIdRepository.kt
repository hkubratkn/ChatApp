package com.kapirti.ira.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserIdRepository(private val context: Context){
    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_id_pref")
        val USER_ID_KEY = stringPreferencesKey(name = "user_id")
    }

    suspend fun saveUserIdState(type: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = type
        }
    }

    fun readUserIdState(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val editTypeState = preferences[USER_ID_KEY] ?: "55555"
                editTypeState
            }
    }
}

