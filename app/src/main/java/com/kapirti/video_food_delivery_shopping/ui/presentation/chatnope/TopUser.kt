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

package com.kapirti.video_food_delivery_shopping.ui.presentation.chatnope

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    title: String,
    online: Boolean,
    startAction: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Box(modifier) {
                IconButton(onClick = startAction) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = title)

            }
            /**Column(horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.material3.Text(
            text = title,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            androidx.compose.material3.Text(
            text = user?.let {
            if(it.online){
            stringResource(id = R.string.online)
            } else {
            it.lastSeen?.let { itTime ->
            timeCustomFormat(itTime.seconds)
            }
            }
            } ?: "",
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
            )
            }*/
        },
        actions = {
            Icon(Icons.Default.Circle, null, tint = if (online) Color.Green else Color.DarkGray)
        }
    )
}
