package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.settings

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.DELETE
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.FEEDBACK
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.LANG
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.LangRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.repository.SettingsRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemePreferencesUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemeUpdateUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.PublishThemeUpdateUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.SaveThemePreferencesUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val editTypeRepository: EditTypeRepository,
    private val langRepository: LangRepository,
    private val settingsRepository: SettingsRepository,
    private val saveThemePreferencesUseCase: SaveThemePreferencesUseCase,
    private val publishThemeUpdateUseCase: PublishThemeUpdateUseCase,
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase,
    private val getThemeUpdateUseCase: GetThemeUpdateUseCase,
): QuickChatViewModel(logService) {
    val hasUser = accountService.hasUser

    private val _theme by lazy { mutableStateOf(Theme.Light) }
    val theme: State<Theme> by lazy { _theme.apply { updateTheme() } }

    private val _lang = mutableStateOf<String?>(null)
    val lang: String?
        get() = _lang.value

    init {
        launchCatching {
            langRepository.readLangState().collect{ itLang ->
                _lang.value = itLang
                getThemeUpdateUseCase().collect { _theme.value = it }
            }
        }
    }

    private fun updateTheme() {
        launchCatching {
            _theme.value = Theme.valueOf(getThemePreferencesUseCase())
        }
    }


    fun onThemeChanged(theme: Theme) {
        launchCatching {
            saveThemePreferencesUseCase(theme.name)
            publishThemeUpdateUseCase(theme)
        }
    }


    fun share() { launchCatching { settingsRepository.share() } }
    fun rate() { launchCatching { settingsRepository.rate() } }

    fun onFeedbackClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(FEEDBACK)
            navigateEdit()
        }
    }
    fun onLangClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(LANG)
            navigateEdit()
        }
    }

    fun onDeleteClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(DELETE)
            navigateEdit()
        }
    }
    fun onSignOutClick(restartApp: () -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp()
        }
    }

    fun privacyPolicy(snackbarHostState: SnackbarHostState,) {
        launchCatching {
            try {
                settingsRepository.privacyPolicy()
            } catch (e: Exception){
                snackbarHostState.showSnackbar(e.message ?: "")
            }
        }
    }
}
