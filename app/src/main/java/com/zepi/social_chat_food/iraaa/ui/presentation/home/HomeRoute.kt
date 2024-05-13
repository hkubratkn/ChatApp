package com.zepi.social_chat_food.iraaa.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.HomeTopAppBar
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds.ADS_HOME_BANNER_ID
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.iraaa.ui.presentation.home.filter.FilterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    openDrawer: () -> Unit,
    navigateSearch: () -> Unit,
    navigateUserProfile: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    var filtersVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { HomeTopAppBar(
            openDrawer = openDrawer,
            onSearchClick = navigateSearch,
            onFilterClick = { filtersVisible = true }
        ) },
        bottomBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        HomeScreen(
            loading = uiState.isLoading,
            items = uiState.items,
            refresh = viewModel::refresh,
            onUserClick = {
                includeUserUidViewModel.addUserUid(it.uid)
                navigateUserProfile()
            },
            scrollState = scrollState,
            modifier = Modifier.background(Color.White).padding(innerPadding)
        )
    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(onDismiss = { filtersVisible = false })
    }
}
