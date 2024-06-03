package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.welcome

import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.OnBoardingRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LangRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    logService: LogService,
    private val repository: OnBoardingRepository,
    private val langRepository: LangRepository
): QuickChatViewModel(logService) {

    fun saveOnBoardingState(completed: Boolean, openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            repository.saveOnBoardingState(completed = completed)
            langRepository.saveLangState(Locale.getDefault().getDisplayLanguage())
           // openAndPopUp(HOME_SCREEN, WELCOME_SCREEN)
        }
    }
}
