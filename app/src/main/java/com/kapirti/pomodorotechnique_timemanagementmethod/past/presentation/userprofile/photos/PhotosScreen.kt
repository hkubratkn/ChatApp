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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile.photos
/**
import androidx.compose.material.icons.Icons
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.common.EmptyContent
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.StaggeredVerticalGrid
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos

@Composable
fun PhotosScreen(
    userPhotos: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>,
    modifier: Modifier = Modifier,
) {
    if (userPhotos.isEmpty()){
        EmptyContent(icon = Icons.Default.PhotoAlbum, label = AppText.no_photos_all)
    } else {
        PhotoBody(
            userPhotos = userPhotos,
            modifier = modifier,
        )
    }
}



@Composable
private fun PhotoBody(
    userPhotos: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .selectableGroup()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            userPhotos.forEach { userPhoto ->
                PhotoSingle(
                    imageId = userPhoto.photo,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun PhotoSingle(
    imageId: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            NoSurfaceImage(
                imageUrl = imageId,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
        }
    }
}
*/
