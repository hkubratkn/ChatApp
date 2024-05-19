package com.kapirti.video_food_delivery_shopping.ui.presentation.chats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.kapirti.video_food_delivery_shopping.R
import com.kapirti.video_food_delivery_shopping.common.EmptyContentChats
import com.kapirti.video_food_delivery_shopping.common.composable.LoadingContent
import com.kapirti.video_food_delivery_shopping.model.Chat

@Composable
fun ChatsScreen(
    uiState: ChatsUiState,
    onRefresh: () -> Unit,
    contentPadding: PaddingValues,
    onChatClicked: (chatId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ChatsContent(
        loading = uiState.isLoading,
        chats = uiState.items,
        onRefresh = onRefresh,
        contentPadding = contentPadding,
        onChatClicked = onChatClicked,
        modifier = modifier,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ChatsContent(
    loading: Boolean,
    chats: List<Chat>,
    onRefresh: () -> Unit,
    contentPadding: PaddingValues,
    onChatClicked: (chatId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadingContent(
        loading = loading,
        empty = chats.isEmpty() && !loading,
        emptyContent = { EmptyContentChats(modifier = modifier) },
        onRefresh = onRefresh
    ) {
        @SuppressLint("InlinedApi") // Granted at install time on API <33.
        val notificationPermissionState = rememberPermissionState(
            android.Manifest.permission.POST_NOTIFICATIONS,
        )
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
        ) {
            if (!notificationPermissionState.status.isGranted) {
                item {
                    NotificationPermissionCard(
                        shouldShowRationale = notificationPermissionState.status.shouldShowRationale,
                        onGrantClick = {
                            notificationPermissionState.launchPermissionRequest()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    )
                }
            }
            items(items = chats) { chat ->
                ChatRow(
                    chat = chat,
                    onClick = { onChatClicked(chat.chatId)},//chat.chatWithLastMessage.id) },
                )
            }
        }
    }
}


@Composable
private fun NotificationPermissionCard(
    shouldShowRationale: Boolean,
    onGrantClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.permission_message),
            modifier = Modifier.padding(16.dp),
        )
        if (shouldShowRationale) {
            Text(
                text = stringResource(R.string.permission_rationale),
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            Button(onClick = onGrantClick) {
                Text(text = stringResource(R.string.permission_grant))
            }
        }
    }
}
