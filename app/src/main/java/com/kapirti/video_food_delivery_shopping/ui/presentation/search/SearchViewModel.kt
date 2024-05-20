package com.kapirti.video_food_delivery_shopping.ui.presentation.search

import com.kapirti.video_food_delivery_shopping.core.room.recent.Recent
import com.kapirti.video_food_delivery_shopping.core.room.recent.RecentDao
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val recentDao: RecentDao,
    logService: LogService
): ZepiViewModel(logService){
    val users = firestoreService.users
    val recents = recentDao.recents()

    fun onSearchClick(user: com.kapirti.video_food_delivery_shopping.model.User, navigateAndPopUpSearchToUserProfile: () -> Unit,){
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
