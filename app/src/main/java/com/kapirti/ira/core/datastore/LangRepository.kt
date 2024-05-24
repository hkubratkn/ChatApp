package com.kapirti.ira.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.kapirti.ira.core.constants.Cons.DEFAULT_LANGUAGE_CODE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class LangRepository (private val context: Context) {
    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lang_pref")
        val LANGUAGE_KEY = stringPreferencesKey(name = "lang_code")
    }

    suspend fun saveLangState(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    fun readLangState(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val languageState = preferences[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE_CODE
                languageState
            }
    }
}
