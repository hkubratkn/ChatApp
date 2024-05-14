package com.zepi.social_chat_food.ui.presentation.settings

import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.runtime.mutableStateOf
import com.zepi.social_chat_food.core.constants.EditType.DELETE
import com.zepi.social_chat_food.core.constants.EditType.FEEDBACK
import com.zepi.social_chat_food.core.constants.EditType.LANG
import com.zepi.social_chat_food.iraaa.core.Async
import com.zepi.social_chat_food.iraaa.core.WhileUiSubscribed
import com.zepi.social_chat_food.core.constants.EditType.PHOTO
import com.zepi.social_chat_food.core.datastore.EditTypeRepository
import com.zepi.social_chat_food.core.datastore.LangRepository
import com.zepi.social_chat_food.core.repository.SettingsRepository
import com.zepi.social_chat_food.core.usecase.GetThemePreferencesUseCase
import com.zepi.social_chat_food.core.usecase.GetThemeUpdateUseCase
import com.zepi.social_chat_food.core.usecase.PublishThemeUpdateUseCase
import com.zepi.social_chat_food.core.usecase.SaveThemePreferencesUseCase
import com.zepi.social_chat_food.model.Theme
import com.zepi.social_chat_food.model.User
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel

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
): QChatViewModel(logService) {
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

    fun privacyPolicy() {
        launchCatching {
            try {
                settingsRepository.privacyPolicy()
            } catch (e: Exception){
                // onShowSnackbar(genericError, "")
            }
        }
    }
}
