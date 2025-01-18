/*
 * Copyright (C) 2025 The Android Open Source Project
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

package com.test.test.ui.presentation.calls

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.test.ui.presentation.conversations.ChatListViewModel

@Composable
fun CallHistory(
    viewModel: CallHistoryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    Surface(
        //modifier = Modifier.padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        CallList(
            recordings = uiState.callRecords
        ) { _, recording ->
            //android.util.Log.d("myTag","Call recording ${recording.id} clicked")
        }
    }
}
