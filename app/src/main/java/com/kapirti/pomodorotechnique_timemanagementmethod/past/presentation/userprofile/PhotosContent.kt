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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile
/**
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.QChatSurface
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.getYearFromTimeStamp
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosContent(
    photos: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = stringResource(AppText.selfie_skills),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = onLongClick,
                        onLongClickLabel = stringResource(id = AppText.long_click),
                    ),
            )
        }
        Photos(photos)
    }
}

@Composable
private fun Photos(
    photos: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
    ) {
        items(photos) { hobby ->
            PhotoItem(hobby)
        }
    }
}

@Composable
private fun PhotoItem(
    userPhoto: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos,
    modifier: Modifier = Modifier
) {
    val year = userPhoto.date?.let { getYearFromTimeStamp(it.seconds) }

    QChatSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            NoSurfaceImage(
                imageUrl = userPhoto.photo,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = year ?: "2024",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
*/