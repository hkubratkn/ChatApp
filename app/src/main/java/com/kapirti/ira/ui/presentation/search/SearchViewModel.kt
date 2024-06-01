package com.kapirti.ira.ui.presentation.search

import com.kapirti.ira.core.room.recent.Recent
import com.kapirti.ira.core.room.recent.RecentDao
import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.model.service.FirestoreService
import com.kapirti.ira.ui.presentation.QuickChatViewModel
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

    fun onSearchClick(user: com.kapirti.ira.model.User, navigateAndPopUpSearchToUserProfile: () -> Unit,){
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
