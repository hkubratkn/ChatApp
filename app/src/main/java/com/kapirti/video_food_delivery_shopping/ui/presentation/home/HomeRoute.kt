/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.video_food_delivery_shopping.ui.presentation.home

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
import com.kapirti.video_food_delivery_shopping.R
import com.kapirti.video_food_delivery_shopping.common.EmptyContent
import com.kapirti.video_food_delivery_shopping.common.composable.AdsBannerToolbar
import com.kapirti.video_food_delivery_shopping.common.composable.HomeTopAppBar
import com.kapirti.video_food_delivery_shopping.core.constants.ConsAds.ADS_HOME_BANNER_ID
import com.kapirti.video_food_delivery_shopping.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.kapirti.video_food_delivery_shopping.ui.presentation.home.filter.FilterScreen
//import com.zepi.social_chat_food.ui.presentation.timeline.EmptyTimeline
//import com.zepi.social_chat_food.ui.presentation.timeline.TimelineVerticalPager

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
                            onUserClick = {
                                includeUserUidViewModel.addUserUid(it.uid)
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
