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

package com.zepi.social_chat_food.ui.presentation.profile

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import com.zepi.social_chat_food.R
import com.zepi.social_chat_food.iraaa.common.EmptyContent
import com.zepi.social_chat_food.iraaa.common.composable.LoadingContent
import com.zepi.social_chat_food.iraaa.common.composable.NoSurfaceImage
import com.zepi.social_chat_food.iraaa.common.composable.StaggeredVerticalGrid
import com.zepi.social_chat_food.iraaa.common.ext.tabContainerModifier
import com.zepi.social_chat_food.model.UserPhotos
import com.zepi.social_chat_food.ui.presentation.profile.ext.InterestsAdaptiveContentLayout


@Composable
fun TabWithPhotos(
    selectedPhotos: SelectedUserPhotosUiState,
    onRefresh: () -> Unit,
    onAssetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        PhotosContent(
            loading = selectedPhotos.isLoading,
            items = selectedPhotos.items,
            onRefresh = onRefresh,
            onAssetClick = onAssetClick,
            modifier = modifier,
        )

    }
}


@Composable
private fun PhotosContent(
    loading: Boolean,
    items: List<com.zepi.social_chat_food.model.UserPhotos>,
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
                label = R.string.user_photos_not_found,
                modifier = modifier
            )
        },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .selectableGroup()
                .padding(horizontal = 16.dp)
//                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
        ) {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = Modifier.padding(4.dp)
            ) {
                items.forEach { course ->
                    val selected = true //val selected = course == selectedAnswer
                    RadioButtonLang(
                        modifier = Modifier.padding(vertical = 8.dp),
                        date = course.date.toString(),
                        photo = course.photo,
                        selected = selected,
                        onOptionSelected = { }//onOptionSelected(course) }
                    )
                }
            }
        }
        /** items.forEach { item ->
        HomeTopItem(
        topAsset = item,
        onAssetClick = { onAssetClick(item) }
        )
        }*/
    }
}


@Composable
private fun RadioButtonLang(
    date: String,
    photo: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            ),
        color = if (selected) { MaterialTheme.colorScheme.primaryContainer } else { MaterialTheme.colorScheme.surface },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            NoSurfaceImage(
                imageUrl = photo,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
            Text(date)
        }
    }
}
