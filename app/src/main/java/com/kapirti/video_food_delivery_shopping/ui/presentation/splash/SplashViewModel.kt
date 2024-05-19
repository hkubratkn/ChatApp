package com.kapirti.video_food_delivery_shopping.ui.presentation.splash

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.video_food_delivery_shopping.core.datastore.OnBoardingRepository
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.ui.ZepiDestinations
import com.kapirti.video_food_delivery_shopping.model.service.ConfigurationService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val onBoardingRepository: OnBoardingRepository,
    private val accountService: AccountService,
    logService: LogService,
): ZepiViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUpSplashToHome: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        showError.value = false
        checkState(openAndPopUpSplashToHome, openAndPopUpSplashToLogin)
    }

    private fun checkState(openAndPopUpSplashToHome: () -> Unit, openAndPopUpSplashToLogin: () -> Unit,) {
        launchCatching(snackbar = false) {
            try {
                if (accountService.hasUser) {
                    openAndPopUpSplashToHome()
                } else {
                    openAndPopUpSplashToLogin()

               // onBoardingRepository.readOnBoardingState().collect { completed ->
                 //   openAndPopUp(ZepiDestinations.HOME_ROUTE, ZepiDestinations.SPLASH_ROUTE)

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
