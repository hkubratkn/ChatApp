package com.test.test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.LogService
import com.test.test.ui.presentation.AppViewModel
import com.test.test.ui.presentation.settings.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService
) : AppViewModel(logService) {
    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous, it.email) }

    private var job: Job? = null

    fun saveIsOnline() {
        launchCatching {
            firestoreService.setUserOnline()
        }
    }

    fun saveLastSeen() {
        launchCatching {
            firestoreService.setUserOffline()
        }
    }
}
