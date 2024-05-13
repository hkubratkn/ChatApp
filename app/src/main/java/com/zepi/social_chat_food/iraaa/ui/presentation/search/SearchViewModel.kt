package com.zepi.social_chat_food.iraaa.ui.presentation.search

import com.zepi.social_chat_food.iraaa.core.room.recent.Recent
import com.zepi.social_chat_food.iraaa.core.room.recent.RecentDao
import com.zepi.social_chat_food.iraaa.model.User
import com.zepi.social_chat_food.iraaa.model.service.FirestoreService
import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val recentDao: RecentDao,
    logService: LogService
): QChatViewModel(logService){
    val users = firestoreService.usersAll
    val recents = recentDao.recents()

    fun onSearchClick(user: User, navigateAndPopUpSearchToUserProfile: () -> Unit,){
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
