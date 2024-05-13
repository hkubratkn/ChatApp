package com.zepi.social_chat_food.iraaa.core.datastore

import com.zepi.social_chat_food.iraaa.model.Theme
import kotlinx.coroutines.flow.SharedFlow

internal interface LocalDataSource {
    suspend fun getThemeUpdate(): SharedFlow<Theme>
    suspend fun publishThemeUpdate(theme: Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(theme: String)
}
