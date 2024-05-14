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

package com.zepi.social_chat_food.ui.presentation.search

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.common.composable.NoSurfaceImage
import com.zepi.social_chat_food.iraaa.core.room.recent.Recent

@Composable
fun SearchSuggestions(
    suggestions: List<Recent>,
    onDeleteClick: (Recent) -> Unit,
    onSuggestionSelect: (String) -> Unit
) {
    LazyColumn {
        item {
            SuggestionHeader(stringResource(AppText.recent_searches))
        }
        items(suggestions, key = { it.id }) {recent ->
            Suggestion(
                recent = recent,
                onDeleteClick = onDeleteClick,
                onSuggestionSelect = onSuggestionSelect,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
        item {
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun SuggestionHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xffded6fe),
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}

@Composable
private fun Suggestion(
    recent: Recent,
    onDeleteClick: (Recent) -> Unit,
    onSuggestionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        NoSurfaceImage(
            imageUrl = recent.photo,
            contentDescription = recent.displayName,
            modifier = Modifier.padding(end = 10.dp).size(40.dp).clip(CircleShape)
        )
        Text(
            text = recent.displayName,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
                .clickable { onSuggestionSelect(recent.displayName) }
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(24.dp).border(1.dp, Color.Red, CircleShape)
                .clickable { onDeleteClick(recent) }
        )
    }
}
