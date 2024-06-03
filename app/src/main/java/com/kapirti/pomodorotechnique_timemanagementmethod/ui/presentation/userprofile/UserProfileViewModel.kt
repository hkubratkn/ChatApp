package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.userprofile

import com.kapirti.pomodorotechnique_timemanagementmethod.core.datastore.UserIdRepository
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.model.UserPhotos
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val userIdRepository: UserIdRepository,
    logService: LogService,
): QuickChatViewModel(logService) {
    val hasUser = accountService.hasUser
    val uid = accountService.currentUserId

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    private val _userPhotos = MutableStateFlow<List<UserPhotos>>(emptyList())
    val userPhotos: StateFlow<List<UserPhotos>> = _userPhotos

    fun initialize(userUid: String) {
        launchCatching {
            userIdRepository.saveUserIdState(userUid)
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
                firestoreService.getUserPhotos(itUser.uid).collect{ itPhotos ->
                    _userPhotos.value = itPhotos
                }
            }
        }
    }
}
