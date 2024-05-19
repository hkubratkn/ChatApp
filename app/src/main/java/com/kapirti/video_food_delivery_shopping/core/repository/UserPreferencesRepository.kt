package com.kapirti.video_food_delivery_shopping.core.repository

import kotlinx.coroutines.flow.SharedFlow

interface UserPreferencesRepository {
    suspend fun getThemeUpdate(): SharedFlow<com.kapirti.video_food_delivery_shopping.model.Theme>
    suspend fun publishThemeUpdate(theme: com.kapirti.video_food_delivery_shopping.model.Theme)
    suspend fun getThemePreferences(): String
    suspend fun saveThemePreferences(value: String)
}
