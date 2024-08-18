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

import android.app.Activity
import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.isValidEmail
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_LOGIN_INTERSTITIAL_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.CountryRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val countryRepository: CountryRepository,
    logService: LogService,
): PomodoroViewModel(logService) {
    private var mInterstitialAd: InterstitialAd? = null
    var uiState = mutableStateOf(LogInUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val button
        get() = uiState.value.button

    fun initialize(context: Context) {
        loadInterstitialAd(context)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLogInClick(
        restartApp: () -> Unit, snackbarHostState: SnackbarHostState,
        emailError: String, emptyPasswordError: String, wrongPasswordError: String,
        navigateAndPopUpLoginToRegister: () -> Unit,
        context: Context
        ) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching {
                onIsErrorEmailChange(true)
                snackbarHostState.showSnackbar(emailError)
                onButtonChange()
            }
            return
        }

        if (password.isBlank()) {
            launchCatching {
                onIsErrorPasswordChange(true)
                snackbarHostState.showSnackbar(emptyPasswordError)
                onButtonChange()
            }
            return
        }

        launchCatching {
            try {
                showInterstitialAd(context)
                accountService.authenticate(email, password)
                val user = firestoreService.getUser(accountService.currentUserId)
                countryRepository.saveCountryState(
                    user?.let { it.country } ?:
                    Locale.getDefault().getDisplayCountry()
                )
                restartApp()
            } catch (ex: FirebaseAuthException) {
                showInterstitialAd(context)

                if (ex.errorCode == "ERROR_WRONG_PASSWORD"){
                    launchCatching {
                        onIsErrorPasswordChange(true)
                        snackbarHostState.showSnackbar(wrongPasswordError)
                        onButtonChange()
                    }
                } else if (ex.errorCode == "ERROR_USER_NOT_FOUND"){
                    navigateAndPopUpLoginToRegister()
                }

                throw ex
            }
        }
    }

    fun onForgotPasswordClick(
        snackbarHostState: SnackbarHostState,
        emailError: String, recoveryEmailSent: String,
        context: Context
    ) {
        if (!email.isValidEmail()) {
            launchCatching {
                onIsErrorEmailChange(true)
                snackbarHostState.showSnackbar(emailError)
            }
            return
        }

        launchCatching {
            showInterstitialAd(context)
            accountService.sendRecoveryEmail(email)
            onIsErrorEmailChange(false)
            snackbarHostState.showSnackbar(recoveryEmailSent)
        }
    }

    private fun showInterstitialAd(context: Context){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(context as Activity)
            loadInterstitialAd(context)
        } else {
            loadInterstitialAd(context)
        }
    }

    private fun loadInterstitialAd(context: Context) {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            ADS_LOGIN_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun onButtonChange() { uiState.value = uiState.value.copy(button = !button) }
    private fun onIsErrorEmailChange(newValue: Boolean) { uiState.value = uiState.value.copy(isErrorEmail = newValue) }
    private fun onIsErrorPasswordChange(newValue: Boolean) { uiState.value = uiState.value.copy(isErrorPassword = newValue) }

}

