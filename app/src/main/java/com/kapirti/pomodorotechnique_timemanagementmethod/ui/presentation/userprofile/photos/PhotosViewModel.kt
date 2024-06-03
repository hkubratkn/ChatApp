package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.userprofile.photos

import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.EditType.PHOTOS
import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.EditTypeRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.UserPhotos
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val editTypeRepository: EditTypeRepository,
    logService: LogService
): QuickChatViewModel(logService) {
    private val _userPhotos = MutableStateFlow<List<UserPhotos>>(emptyList())
    val userPhotos: StateFlow<List<UserPhotos>> = _userPhotos

    val currentUserId = accountService.currentUserId

    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUserPhotos(userUid).collect { itPhotos ->
                _userPhotos.value = itPhotos
            }
        }
    }
    fun onAddClick(navigateEdit: () -> Unit){
        launchCatching {
            editTypeRepository.saveEditTypeState(PHOTOS)
            navigateEdit()
        }
    }
}
