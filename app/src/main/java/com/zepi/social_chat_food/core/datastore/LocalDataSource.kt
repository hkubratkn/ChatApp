package com.zepi.social_chat_food.core.datastore

import com.zepi.social_chat_food.model.Theme
import kotlinx.coroutines.flow.SharedFlow

internal interface LocalDataSource {
    suspend fun getThemeUpdate(): SharedFlow<com.zepi.social_chat_food.model.Theme>
    suspend fun publishThemeUpdate(theme: com.zepi.social_chat_food.model.Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(theme: String)
}
