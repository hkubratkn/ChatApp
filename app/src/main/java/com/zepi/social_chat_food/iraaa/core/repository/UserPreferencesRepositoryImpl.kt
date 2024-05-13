package com.zepi.social_chat_food.iraaa.core.repository

import com.zepi.social_chat_food.iraaa.core.datastore.LocalDataSource
import com.zepi.social_chat_food.iraaa.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.SharedFlow

internal class UserPreferencesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : UserPreferencesRepository {
    override suspend fun getThemeUpdate(): SharedFlow<Theme> = localDataSource.getThemeUpdate()

    override suspend fun publishThemeUpdate(theme: Theme) =
        localDataSource.publishThemeUpdate(theme)

    override suspend fun getThemePreferences(): String = localDataSource.getThemePreferences()

    override suspend fun saveThemePreferences(theme: String) =
        localDataSource.saveThemePreferences(theme)

}
