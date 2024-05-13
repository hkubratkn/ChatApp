package com.zepi.social_chat_food.iraaa

import androidx.compose.runtime.Stable
import com.zepi.social_chat_food.iraaa.core.data.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class QChatAppState(
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
) {
    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}


/**
import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.kapirti.ira.common.snackbar.SnackbarManager
import com.kapirti.ira.common.snackbar.SnackbarMessage.Companion.toMessage
import com.kapirti.ira.core.data.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class QChatAppState(
val scaffoldState: ScaffoldState,
private val snackbarManager: SnackbarManager,
private val resources: Resources,
coroutineScope: CoroutineScope,
networkMonitor: NetworkMonitor,
) {




fun navigate(route: String) {
navController.navigate(route) { launchSingleTop = true }
}




}*/
