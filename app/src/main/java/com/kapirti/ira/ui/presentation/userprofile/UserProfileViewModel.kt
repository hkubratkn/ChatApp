package com.kapirti.ira.ui.presentation.userprofile

import com.kapirti.ira.core.datastore.UserIdRepository
import com.kapirti.ira.model.User
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.soci.ui.stateInUi
import com.kapirti.ira.ui.presentation.QuickChatViewModel
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

    val photos = firestoreService.userPhotos.stateInUi(emptyList())

    fun initialize(userUid: String) {
        launchCatching {
            userIdRepository.saveUserIdState(userUid)
            firestoreService.getUser(userUid)?.let { itUser ->
                _user.value = itUser
            }
        }
    }
}
