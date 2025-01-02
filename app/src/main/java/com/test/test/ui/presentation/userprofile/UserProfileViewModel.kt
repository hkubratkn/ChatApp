
package com.test.test.ui.presentation.userprofile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.LogService
import com.test.test.ui.presentation.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    logService: LogService,
): AppViewModel(logService) {

    var uiState = mutableStateOf(UserProfileUiState())
        private set

    fun getUser(userId: String) = viewModelScope.launch {
        val x = firestoreService.getUser(userId)
        uiState.value = uiState.value.copy(user = x)
    }
}
