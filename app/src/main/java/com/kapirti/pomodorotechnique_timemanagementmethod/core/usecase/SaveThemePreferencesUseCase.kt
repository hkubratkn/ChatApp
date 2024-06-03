package com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase

import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveThemePreferencesUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(theme: String) = repository.saveThemePreferences(theme)
}
