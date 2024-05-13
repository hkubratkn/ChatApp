package com.zepi.social_chat_food.iraaa.core.usecase

import com.zepi.social_chat_food.iraaa.core.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveThemePreferencesUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(theme: String) = repository.saveThemePreferences(theme)
}
