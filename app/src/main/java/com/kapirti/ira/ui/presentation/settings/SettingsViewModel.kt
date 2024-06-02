package com.kapirti.ira.ui.presentation.settings

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import com.kapirti.ira.core.constants.EditType.DELETE
import com.kapirti.ira.core.constants.EditType.FEEDBACK
import com.kapirti.ira.core.constants.EditType.LANG
import com.kapirti.ira.core.constants.EditType.PROFILE
import com.kapirti.ira.core.datastore.EditTypeRepository
import com.kapirti.ira.core.datastore.LangRepository
import com.kapirti.ira.core.repository.SettingsRepository
import com.kapirti.ira.core.usecase.GetThemePreferencesUseCase
import com.kapirti.ira.core.usecase.GetThemeUpdateUseCase
import com.kapirti.ira.core.usecase.PublishThemeUpdateUseCase
import com.kapirti.ira.core.usecase.SaveThemePreferencesUseCase
import com.kapirti.ira.model.Theme
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.QuickChatViewModel

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
