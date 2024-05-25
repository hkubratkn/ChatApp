package com.kapirti.ira.ui.presentation.profile

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.MenuToolbar
import com.kapirti.ira.core.constants.ConsAds.ADS_PROFILE_BANNER_ID
import com.kapirti.ira.R.string as AppText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfileRoute(
    navigateEdit: () -> Unit,
    showInterstialAd: () -> Unit,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    // includeAssetViewModel: IncludeAssetViewModel,
    // navigateToAssetDetail: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user = viewModel.user.collectAsStateWithLifecycle()
    val photos by viewModel.photos.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ads = ADS_PROFILE_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.profile_title,
                actionsIcon = Icons.Default.AccountCircle,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        ProfileScreen(
            user = user.value,
            modifier = Modifier.padding(innerPadding),
            photos = photos
        )
    }
}


/**
    // val selectedAsset by viewModel.selectedAsset.collectAsStateWithLifecycle()


    val tabContent = rememberTabContent(
        selectedPhotos = selectedPhotos,
        //selectedAssets = selectedAsset,
        onRefresh = viewModel::refresh,
        onAssetClick = {
            // includeAssetViewModel.addAsset(it)
            // navigateToAssetDetail()
        },
    )
    val (currentSection, updateSection) = rememberSaveable {
        mutableStateOf(tabContent.first().section)
    }



}
*/
