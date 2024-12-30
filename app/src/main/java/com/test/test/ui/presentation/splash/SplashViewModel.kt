package com.test.test.ui.presentation.splash


import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.test.test.model.service.AccountService
import com.test.test.model.service.ConfigurationService
import com.test.test.model.service.LogService
import com.test.test.ui.presentation.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    configurationService: ConfigurationService,
    logService: LogService,
): AppViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }


    fun onAppStart(
        navigateAndPopUpSplashToHome: () -> Unit,
        navigateAndPopUpSplashToLogin: () -> Unit,
    ) {
        showError.value = false
        launchCatching {
            if (accountService.hasUser) {
                navigateAndPopUpSplashToHome()
            } else {
                navigateAndPopUpSplashToLogin()
            }

        }
    }
}
