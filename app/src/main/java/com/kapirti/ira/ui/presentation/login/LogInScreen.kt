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

package com.kapirti.ira.ui.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.ira.R.string as AppText
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.BasicButton
import com.kapirti.ira.common.composable.BasicTextButton
import com.kapirti.ira.common.composable.EmailField
import com.kapirti.ira.common.composable.HeaderText
import com.kapirti.ira.common.composable.PasswordField
import com.kapirti.ira.common.ext.basicButton
import com.kapirti.ira.common.ext.fieldModifier
import com.kapirti.ira.common.ext.smallSpacer
import com.kapirti.ira.common.ext.textButton
import com.kapirti.ira.core.constants.ConsAds.ADS_LOG_IN_BANNER_ID

@Composable
fun LogInScreen(
    restartApp: () -> Unit,
    loginToRegister: () -> Unit,
    showInterstialAd: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val emailError = stringResource(id = AppText.email_error)
    val emptyPasswordError = stringResource(id = AppText.empty_password_error)
    val recoveryEmailSent = stringResource(id = AppText.recovery_email_sent)

    AdsBannerToolbar(ads = ADS_LOG_IN_BANNER_ID)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HeaderText(text = AppText.welcome_back)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(imageVector = Icons.Default.Login, contentDescription = null)
        }

        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(text = AppText.log_in, Modifier.basicButton(), uiState.button) {
            showInterstialAd()
            viewModel.onLogInClick(
                restartApp = restartApp,
                onShowSnackbar = onShowSnackbar,
                emailError = emailError,
                emptyPasswordError = emptyPasswordError
            )
        }

        BasicTextButton(AppText.forgotten_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick(
                onShowSnackbar = onShowSnackbar,
                emailError = emailError,
                recoveryEmailSent = recoveryEmailSent
            )
            showInterstialAd()
        }

        Spacer(modifier = Modifier.smallSpacer())

        BasicTextButton(AppText.create_new_account, Modifier.textButton()) {
            showInterstialAd()
            loginToRegister()
        }
    }
}
