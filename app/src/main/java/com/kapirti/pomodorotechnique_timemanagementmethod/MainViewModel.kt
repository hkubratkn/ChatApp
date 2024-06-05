package com.kapirti.pomodorotechnique_timemanagementmethod

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestoreException
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemePreferencesUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.core.usecase.GetThemeUpdateUseCase
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Theme
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.PomodoroViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase,
    private val getThemeUpdateUseCase: GetThemeUpdateUseCase,
    logService: LogService
) : PomodoroViewModel(logService) {
    val hasUser = accountService.hasUser

    private val _theme by lazy { mutableStateOf(Theme.Light) }
    val theme: State<Theme> by lazy { _theme.apply { updateTheme() } }

    init {
        launchCatching {
            getThemeUpdateUseCase().collect { _theme.value = it }
        }
    }

    private fun updateTheme() {
        launchCatching {
            _theme.value = Theme.valueOf(getThemePreferencesUseCase())
        }
    }

    private var job: Job? = null

    fun saveIsOnline() {
        launchCatching {
            if (accountService.hasUser) {
                job?.cancel()
                job = launchCatching {
                    try {
                        val response = firestoreService.updateUserOnline(true)
                    } catch (e: FirebaseFirestoreException) {
                        println("raheem: ${e.message}")
                    }
                }
            }
        }
    }

    fun saveLastSeen() {
        launchCatching {
            if (accountService.hasUser) {
                job?.cancel()
                job = launchCatching {
                    try {
                        val responseOne = firestoreService.updateUserOnline(false)
                        val response = firestoreService.updateUserLastSeen()
                    } catch (e: FirebaseFirestoreException) {
                        println("raheem: ${e.message}")
                    }
                }
            }
        }
    }
}
