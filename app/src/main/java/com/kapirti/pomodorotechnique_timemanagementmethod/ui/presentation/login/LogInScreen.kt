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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.login

import android.app.Activity.RESULT_OK
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_LOG_IN_BANNER_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.model.ggoo.GoogleAuthUiClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicTextButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.EmailField
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HeaderText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.PasswordField
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.basicButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.fieldModifier
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.smallSpacer
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.textButton
import com.kapirti.pomodorotechnique_timemanagementmethod.past.common.composable.GoogleSignInRow
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    restartApp: () -> Unit,
    loginToRegister: () -> Unit,
    showInterstialAd: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            viewModel.googleLoginDone(restartApp)
        }
    }


    val uiState by viewModel.uiState
    val emailError = stringResource(id = AppText.email_error)
    val emptyPasswordError = stringResource(id = AppText.empty_password_error)
    val recoveryEmailSent = stringResource(id = AppText.recovery_email_sent)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { AdsBannerToolbar(ads = ADS_LOG_IN_BANNER_ID) }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
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
                Icon(imageVector = Icons.Default.Login, contentDescription = stringResource(id = AppText.log_in))
            }

            EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
            PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

            BasicButton(text = AppText.log_in, Modifier.basicButton(), uiState.button) {
                showInterstialAd()
                viewModel.onLogInClick(
                    restartApp = restartApp,
                    snackbarHostState = snackbarHostState,
                    emailError = emailError,
                    emptyPasswordError = emptyPasswordError
                )
            }

            BasicTextButton(AppText.forgotten_password, Modifier.textButton()) {
                viewModel.onForgotPasswordClick(
                    snackbarHostState = snackbarHostState,
                    emailError = emailError,
                    recoveryEmailSent = recoveryEmailSent
                )
                showInterstialAd()
            }

            Spacer(modifier = Modifier.smallSpacer())
            GoogleSignInRow(
                text = AppText.log_in_with_google,
                onClick = {
                    scope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.smallSpacer())

            BasicTextButton(AppText.create_new_account, Modifier.textButton()) {
                showInterstialAd()
                loginToRegister()
            }
        }
    }
}

