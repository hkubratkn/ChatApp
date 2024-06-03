/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.impl
/**
import com.kapirti.pomodorotechnique_timemanagementmethod.BuildConfig
import com.kapirti.pomodorotechnique_timemanagementmethod.R.xml as AppConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.ConfigurationService
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class ConfigurationServiceImpl @Inject constructor() :
    com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.ConfigurationService {
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
        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.trace(
            FETCH_CONFIG_TRACE
        ) { remoteConfig.fetchAndActivate().await() }

    override val isShowTaskEditButtonConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}
*/
