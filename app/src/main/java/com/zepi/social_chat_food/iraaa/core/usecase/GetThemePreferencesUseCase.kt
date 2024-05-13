package com.zepi.social_chat_food.iraaa.core.usecase

import com.zepi.social_chat_food.iraaa.core.repository.UserPreferencesRepository
import javax.inject.Inject

class GetThemePreferencesUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(): String = repository.getThemePreferences()
}
