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

package com.kapirti.video_food_delivery_shopping.ui.presentation.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kapirti.video_food_delivery_shopping.R.string as AppText
import com.kapirti.video_food_delivery_shopping.R.drawable as AppIcon
import com.kapirti.video_food_delivery_shopping.model.User
import com.kapirti.video_food_delivery_shopping.ui.presentation.profile.ext.InterestsTabRow
import com.kapirti.video_food_delivery_shopping.ui.presentation.profile.ext.ProfileCard
import com.kapirti.video_food_delivery_shopping.ui.presentation.profile.ext.SelectTopicButton

/**
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.common.EmptyContentProfile
import com.kapirti.ira.common.composable.LoadingContent
import com.kapirti.ira.common.composable.SurfaceImage
import com.kapirti.ira.R.string as AppText
import com.kapirti.ira.common.ext.baselineHeight
import com.kapirti.ira.common.ext.smallSpacer
import com.kapirti.ira.core.room.profile.Profile

@Composable
fun ProfileScreen(
    profileToLogin: () -> Unit,
    profileToRegister: () -> Unit,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
    val scrollState = rememberScrollState()
    val displayName = viewModel.displayName

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollInteropConnection)
            .systemBarsPadding()
    ) {
        ProfileContent(
            containerHeight = this@BoxWithConstraints.maxHeight,
            displayName = displayName,

            onDescriptionClicked = {},
            onPhotoClicked = {},
            onBirthdayClicked = {},
            onGenderClicked = {},
            onDisplayNameClicked = {},
            onNameSurnameClicked = {},
            /**            onDisplayNameClicked = { viewModel.onDisplayNameClick(openScreen) },
            onNameSurnameClicked = { viewModel.onNameSurnameClick(openScreen) },
            onGenderClicked = { viewModel.onGenderClick(openScreen) },
            onBirthdayClicked = { viewModel.onBirthdayClick(openScreen) },
            onPhotoClicked = { viewModel.onPhotoClick(openScreen) },
            onDescriptionClicked = { viewModel.onDescriptionClick(openScreen) },
            onLoginClick = { viewModel.onLoginClick(openScreen) },
            onRegisterClick = { viewModel.onRegisterClick(openScreen) },*/
            modifier = modifier,
            scrollState = scrollState
        )
    }
}

@Composable
private fun ProfileContent(

    containerHeight: Dp, displayName: String,

    onDisplayNameClicked: () -> Unit,
    onNameSurnameClicked: () -> Unit,
    onGenderClicked: () -> Unit,
    onBirthdayClicked: () -> Unit,
    onPhotoClicked: () -> Unit,
    onDescriptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {

}

@Composable
private fun UserInfoFields(
    profile: Profile?, displayName: String, containerHeight: Dp,
    onDisplayNameClicked: () -> Unit,
    onNameSurnameClicked: () -> Unit,
    onGenderClicked: () -> Unit,
    onBirthdayClicked: () -> Unit,
    onDescriptionClicked: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        NameAndPosition(profile)

        ProfileProperty(stringResource(AppText.display_name), displayName){ onDisplayNameClicked()}
        ProfileProperty(stringResource(AppText.name_and_surname), profile?.let { "${it.namedb} ${it.surnamedb}"} ?: ""){ onNameSurnameClicked()}
        ProfileProperty(stringResource(AppText.gender), profile?.let { it.genderdb } ?: ""){ onGenderClicked()}
        ProfileProperty(stringResource(AppText.birthday), profile?.let{it.birthdaydb} ?: ""){ onBirthdayClicked()}
        ProfileProperty(stringResource(id = AppText.description), profile?.let { it.description } ?: "") { onDescriptionClicked() }

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun NameAndPosition(profile: Profile?) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Name(
            profile,
            modifier = Modifier.baselineHeight(32.dp)
        )
        Position(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .baselineHeight(24.dp)
        )
    }
}

@Composable
private fun Name(profile: Profile?, modifier: Modifier = Modifier) {
    Text(
        text = profile?.let{"${it.namedb} ${it.surnamedb}"} ?: "",
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun Position(modifier: Modifier = Modifier) {
    Text(
        text = "nope",
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun ProfileHeader(
    scrollState: ScrollState,
    profile: Profile?,
    containerHeight: Dp,
    onPhotoClicked: () -> Unit = { }
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    profile?.let {
        SurfaceImage(
            imageUrl = it.photodb,
            contentDescription = null,
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = offsetDp,
                    end = 16.dp
                )
                .clip(CircleShape),
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)) {
            Button(
                shape = MaterialTheme.shapes.extraSmall,
                onClick = onPhotoClicked
            ) {
                Text(stringResource(AppText.retake_photo))
            }
        }
    }
}

@Composable
private fun ProfileProperty(label: String, value: String, isLink: Boolean = false, onIconClick: () -> Unit = {}) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        Text(
            text = label,
            modifier = Modifier.baselineHeight(24.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val style = if (isLink) {
            MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
        } else {
            MaterialTheme.typography.bodyLarge
        }

        Row(
            modifier = Modifier
                .baselineHeight(24.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                style = style
            )
            Icon(
                Icons.Default.Edit,
                null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onIconClick() }
            )
        }
    }
}*/
/**
enum class Sections(@StringRes val titleResId: Int) {
    Favorites(AppText.interests_section_favorites),
    Assets(AppText.interests_section_assets),
}


class TabContent(val section: Sections, val content: @Composable () -> Unit)
*/
@Composable
fun ProfileScreen(
    profile: com.kapirti.video_food_delivery_shopping.model.User,
   // tabContent: List<TabContent>,
   // currentSection: Sections,
    isExpandedScreen: Boolean,
   // onTabChange: (Sections) -> Unit,
    modifier: Modifier = Modifier,
) {}
  /**  InterestScreenContent(
        photo = profile.photo,
        name = profile.name,
        surname = profile.surname,
        description = profile.description,
        currentSection, isExpandedScreen,
        onTabChange, tabContent, modifier
    )
}

@Composable
fun rememberTabContent(
    selectedPhotos: SelectedUserPhotosUiState,
  //  selectedAssets: SelectedAssetUiState,
    onRefresh: () -> Unit,
    onAssetClick: () -> Unit,
): List<TabContent> {

    val favoritesSection = TabContent(Sections.Favorites) {
        TabWithPhotos(
            selectedPhotos = selectedPhotos,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
        )
    }

    val assetsSection = TabContent(Sections.Assets) {
        TabWithPhotos(
            selectedPhotos = selectedPhotos,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
        )
        /**        TabWithAssets(
            selectedTopics = selectedAssets,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
        )*/
/**        TabWithAssets(
            selectedTopics = selectedAssets,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
        )*/
    }

    return listOf(favoritesSection, assetsSection)
}

@Composable
private fun InterestScreenContent(
    photo: String, name: String, surname: String, description: String,
    currentSection: Sections,
    isExpandedScreen: Boolean,
    updateSection: (Sections) -> Unit,
    tabContent: List<TabContent>,
    modifier: Modifier = Modifier
) {
    val selectedTabIndex = tabContent.indexOfFirst { it.section == currentSection }
    Column(modifier) {

        ProfileCard(photo = photo, name = name, surname = surname)
        Spacer(Modifier.height(10.dp))
        Text(text = description)

        InterestsTabRow(selectedTabIndex, updateSection, tabContent, isExpandedScreen)
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        Box(modifier = Modifier.weight(1f)) {
            // display the current tab content which is a @Composable () -> Unit
            tabContent[selectedTabIndex].content()
        }
    }
}





/**
@Composable
private fun TabWithAssets(
  //  selectedTopics: SelectedAssetUiState,
    onRefresh: () -> Unit,
    onAssetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        AssetsContent(
          //  loading = selectedTopics.isLoading,
            //items = selectedTopics.items,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun AssetsContent(
   // loading: Boolean,
    //items: List<String>,
    onRefresh: () -> Unit,
    onAssetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadingContent(
        loading = loading,
        empty = items.isEmpty() && !loading,
        emptyContent = {
            EmptyContent(
                icon = Icons.Default.Add,
                label = AppText.app_name,
                modifier = modifier
            )
        },
        onRefresh = onRefresh
    ) {
       /** items.forEach { item ->
            HomeTopItem(
                topAsset = item,
                onAssetClick = { onAssetClick(item) }
            )
        }*/
    }
}
*/


@Composable
private fun TopicItem(
    itemTitle: String,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = modifier.toggleable(
                value = selected,
                onValueChange = { onToggle() }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val image = painterResource(AppIcon.ic_launcher_background)
            Image(
                painter = image,
                contentDescription = null, // decorative
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = itemTitle,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f), // Break line if the title is too long
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.width(16.dp))
            SelectTopicButton(selected = selected)
        }
        Divider(
            modifier = modifier.padding(start = 72.dp, top = 8.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}
*/
