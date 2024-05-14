package com.zepi.social_chat_food.iraaa.ui.presentation.splash

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.zepi.social_chat_food.core.datastore.OnBoardingRepository
import com.zepi.social_chat_food.ui.ZepiDestinations
import com.zepi.social_chat_food.model.service.ConfigurationService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val onBoardingRepository: OnBoardingRepository,
    logService: LogService,
): QChatViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        checkState(openAndPopUp)
    }

    private fun checkState(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                onBoardingRepository.readOnBoardingState().collect { completed ->
                    openAndPopUp(ZepiDestinations.HOME_ROUTE, ZepiDestinations.SPLASH_ROUTE)

                    /** if (completed) {
                        openAndPopUp(QChatDestinations.HOME_ROUTE, QChatDestinations.SPLASH_ROUTE)
                    } else {
                        openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
                    }*/
                }
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
        }
    }
}
