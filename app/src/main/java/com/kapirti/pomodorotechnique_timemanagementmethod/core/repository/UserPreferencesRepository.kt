package com.kapirti.pomodorotechnique_timemanagementmethod.core.repository

import kotlinx.coroutines.flow.SharedFlow

interface UserPreferencesRepository {
    suspend fun getThemeUpdate(): SharedFlow<com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme>
    suspend fun publishThemeUpdate(theme: com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(value: String)
}
