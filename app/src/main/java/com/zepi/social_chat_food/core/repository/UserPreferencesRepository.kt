package com.zepi.social_chat_food.core.repository

import com.zepi.social_chat_food.model.Theme
import kotlinx.coroutines.flow.SharedFlow

interface UserPreferencesRepository {
    suspend fun getThemeUpdate(): SharedFlow<com.zepi.social_chat_food.model.Theme>
    suspend fun publishThemeUpdate(theme: com.zepi.social_chat_food.model.Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(value: String)
}
