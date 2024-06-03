package com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase

import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.UserPreferencesRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetThemeUpdateUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Flow<Theme> = repository.getThemeUpdate()
}

