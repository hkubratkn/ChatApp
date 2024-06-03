package com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase

import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.UserPreferencesRepository
import javax.inject.Inject

class GetThemePreferencesUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(): String = repository.getThemePreferences()
}
