package com.zepi.social_chat_food.iraaa.ui.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.MenuToolbar
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds.ADS_PROFILE_BANNER_ID

@Composable
fun ProfileRoute(
    profileToLogin: () -> Unit,
    profileToRegister: () -> Unit,
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedPhotos by viewModel.selectedPhotos.collectAsStateWithLifecycle()
    // val selectedAsset by viewModel.selectedAsset.collectAsStateWithLifecycle()
    val hasUser = viewModel.hasUser

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


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { AdsBannerToolbar(ads = ADS_PROFILE_BANNER_ID) },
        topBar = {
            MenuToolbar(
                text = AppText.profile_title,
                actionsIcon = Icons.Default.AccountCircle,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            ) },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            if (hasUser){
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red),
                    onClick = {
                        viewModel.onAddClick(navigateEdit)
                    }
                ) {
                    Icon(Icons.Default.Add, null)
                }
            }
        }
    ) { innerPadding ->
        ProfileScreen(
            loading = uiState.isLoading,
            profile = uiState.profile,
            onRefresh = viewModel::refresh,
            onLoginClick = profileToLogin,
            onRegisterClick = profileToRegister,
            tabContent = tabContent,
            currentSection = currentSection,
            isExpandedScreen = isExpandedScreen,
            onTabChange = updateSection,
            modifier = Modifier.padding(innerPadding)
        )
       /** ProfileScreen(
            profileToLogin = profileToLogin,
            profileToRegister = profileToRegister,
            showInterstialAd = showInterstialAd,
            modifier = modifier.padding(innerPadding)
        )*/
    }
}
