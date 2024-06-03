package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.search

import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.recent.Recent
import com.kapirti.pomodorotechnique_timemanagementmethod.core.room.recent.RecentDao
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.FirestoreService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val recentDao: RecentDao,
    logService: LogService
): QuickChatViewModel(logService){
    val users = firestoreService.users
    val recents = recentDao.recents()

    fun onSearchClick(user: com.kapirti.pomodorotechnique_timemanagementmethod.model.User, navigateAndPopUpSearchToUserProfile: () -> Unit,){
        launchCatching {
            recentDao.insert(
                Recent(displayName = user.displayName, photo = user.photo)
            )
            navigateAndPopUpSearchToUserProfile()
        }
    }
    fun onDeleteClick(recent: Recent){
        launchCatching {
            recentDao.delete(recent)
        }
    }
}
