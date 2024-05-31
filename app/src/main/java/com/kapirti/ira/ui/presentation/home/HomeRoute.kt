package com.kapirti.ira.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.R
import com.kapirti.ira.common.EmptyContent
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.HomeTopAppBar
import com.kapirti.ira.core.constants.ConsAds.ADS_HOME_BANNER_ID
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.ui.presentation.home.filter.FilterScreen
//import com.zepi.social_chat_food.ui.presentation.timeline.EmptyTimeline
//import com.zepi.social_chat_food.ui.presentation.timeline.TimelineVerticalPager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    openDrawer: () -> Unit,
    navigateSearch: () -> Unit,
    navigateUserProfile: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val users by viewModel.users.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()
    var filtersVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                openDrawer = openDrawer,
                onSearchClick = navigateSearch,
                onFilterClick = { filtersVisible = true }
            )
        },
        bottomBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->

        if (users.isEmpty()) {
            EmptyContent(
                icon = Icons.Default.Person,
                label = R.string.no_users_all,
                modifier
            )
        } else {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyColumn(state = scrollState,) {
                    items(users, key = { it.uid }) { userItem ->
                        UserItem(
                            user = userItem,
                            onClick = {
                                includeUserIdViewModel.addPartnerId(it.uid)
                                navigateUserProfile()
                            }
                        )
                    }
                }
            }
        }
        /**
        HomeScreen(
            loading = uiState.isLoading,
            items = uiState.items,
            refresh = viewModel::refresh,
            onUserClick = {

            },
            scrollState = scrollState,
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
        )*/
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
