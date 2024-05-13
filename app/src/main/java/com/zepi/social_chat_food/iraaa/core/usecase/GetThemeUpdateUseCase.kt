package com.zepi.social_chat_food.iraaa.core.usecase

import com.zepi.social_chat_food.iraaa.core.repository.UserPreferencesRepository
import com.zepi.social_chat_food.iraaa.model.Theme
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetThemeUpdateUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Flow<Theme> = repository.getThemeUpdate()
}
