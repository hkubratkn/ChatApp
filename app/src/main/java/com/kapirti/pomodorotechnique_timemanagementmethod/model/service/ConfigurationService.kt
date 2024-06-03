package com.kapirti.pomodorotechnique_timemanagementmethod.model.service

interface ConfigurationService {
    suspend fun fetchConfiguration(): Boolean
    val isShowTaskEditButtonConfig: Boolean
}
