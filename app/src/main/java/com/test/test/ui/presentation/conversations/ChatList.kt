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

package com.test.test.ui.presentation.conversations

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.test.test.model.ChatRoom
import com.test.test.model.ChatRow

@Composable
fun ChatList(
    //onChatClicked: (chatId: String) -> Unit,
    onChatClicked: (firstId: String, secondId: String, name: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatListViewModel = hiltViewModel(),
) {

//    LaunchedEffect(Unit) {
//        viewModel.ready()
//    }

    //val conv by viewModel.conversations.collectAsStateWithLifecycle()
    //val conv by viewModel.conversations.collectAsState(initial = listOf())

    //val latestConv = conv.map { viewModel.mapChatItem(it) }


    //val chatList by viewModel.chats.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState
    ChatList(uiState.conversationList, onChatClicked, modifier)
    //ChatList(conv, onChatClicked, modifier)

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatList(
    chats: List<ChatRow>,
    //onChatClicked: (chatId: String) -> Unit,
    onChatClicked: (firstId: String, secondId: String, name:String) -> Unit,
    modifier: Modifier = Modifier,
) {
    @SuppressLint("InlinedApi") // Granted at install time on API <33.
    val notificationPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            //HomeAppBar(title = stringResource(TopLevelDestination.ChatsList.label))
        },
    ) { contentPadding ->
        //HomeBackground(modifier = Modifier.fillMaxSize())
        LazyColumn(
            contentPadding = contentPadding,
        ) {
//            if (!notificationPermissionState.status.isGranted) {
//                item {
//                    NotificationPermissionCard(
//                        shouldShowRationale = notificationPermissionState.status.shouldShowRationale,
//                        onGrantClick = {
//                            notificationPermissionState.launchPermissionRequest()
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                    )
//                }
//            }
            items(items = chats) { chat ->
                ChatRow(
                    chat = chat,
                    //onClick = { onChatClicked(chat.chatWithLastMessage.id) },
                    onClick = { name ->  onChatClicked(chat.userIds.first(), chat.userIds.last(), name) },
                )
            }
        }
    }
}

//@Composable
//private fun NotificationPermissionCard(
//    shouldShowRationale: Boolean,
//    onGrantClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Card(
//        modifier = modifier,
//    ) {
//        Text(
//            text = stringResource(R.string.permission_message),
//            modifier = Modifier.padding(16.dp),
//        )
//        if (shouldShowRationale) {
//            Text(
//                text = stringResource(R.string.permission_rationale),
//                modifier = Modifier.padding(horizontal = 16.dp),
//            )
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            contentAlignment = Alignment.TopEnd,
//        ) {
//            Button(onClick = onGrantClick) {
//                Text(text = stringResource(R.string.permission_grant))
//            }
//        }
//    }
//}
