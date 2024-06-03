package com.kapirti.pomodorotechnique_timemanagementmethod.model.service.impl

import com.kapirti.pomodorotechnique_timemanagementmethod.BuildConfig
import com.kapirti.pomodorotechnique_timemanagementmethod.R.xml as AppConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.ConfigurationService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.trace
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {
    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean =
        trace(FETCH_CONFIG_TRACE) { remoteConfig.fetchAndActivate().await() }

    override val isShowTaskEditButtonConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}
