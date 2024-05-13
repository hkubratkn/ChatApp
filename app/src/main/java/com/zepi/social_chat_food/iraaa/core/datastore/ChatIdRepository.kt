package com.zepi.social_chat_food.iraaa.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ChatIdRepository(private val context: Context){
    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "chat_id_pref")
        val CHAT_ID_KEY = stringPreferencesKey(name = "chat_id")
    }

    suspend fun saveChatIdState(type: String) {
        context.dataStore.edit { preferences ->
            preferences[CHAT_ID_KEY] = type
        }
    }

    fun readChatIdState(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val editTypeState = preferences[CHAT_ID_KEY] ?: "55555"
                editTypeState
            }
    }
}
