package com.kapirti.ira.core.repository

import kotlinx.coroutines.flow.SharedFlow

interface UserPreferencesRepository {
    suspend fun getThemeUpdate(): SharedFlow<com.kapirti.ira.model.Theme>
    suspend fun publishThemeUpdate(theme: com.kapirti.ira.model.Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(value: String)
}
