package com.zepi.social_chat_food.model.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
    val isShowTaskEditButtonConfig: Boolean
}
