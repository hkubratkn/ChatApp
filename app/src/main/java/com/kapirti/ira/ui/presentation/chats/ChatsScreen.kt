package com.kapirti.ira.ui.presentation.chats

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.ira.R
import com.kapirti.ira.common.EmptyContent
import com.kapirti.ira.common.composable.InterestsAdaptiveContentLayout
import com.kapirti.ira.common.ext.tabContainerModifier
import com.kapirti.ira.model.Chat
import com.kapirti.ira.ui.presentation.chats.ext.ChatsTabRow


enum class SectionsChats(@StringRes val titleResId: Int) {
    ChatsList(R.string.chats_title),
    ArchiveList(R.string.archive_title)
}

class TabContentChats(val section: SectionsChats, val content: @Composable () -> Unit)

@Composable
fun rememberTabContent(
    chats: List<Chat>,
    archives: List<Chat>,
    onChatClick: (Chat) -> Unit,
): List<TabContentChats> {
    val favoritesSection = TabContentChats(SectionsChats.ChatsList) { TabWithChats(chats = chats, onChatClick = onChatClick) }
    val assetsSection = TabContentChats(SectionsChats.ArchiveList) { TabWithArchives(archives = archives) }

    return listOf(favoritesSection, assetsSection)
}

@Composable
fun ChatsScreen(
    currentSection: SectionsChats,
    isExpandedScreen: Boolean,
    updateSection: (SectionsChats) -> Unit,
    tabContent: List<TabContentChats>,
    modifier: Modifier = Modifier
) {
    val selectedTabIndex = tabContent.indexOfFirst { it.section == currentSection }
    Column(modifier) {
        ChatsTabRow(selectedTabIndex, updateSection, tabContent, isExpandedScreen)
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        Box(modifier = Modifier.weight(1f)) {
            // display the current tab content which is a @Composable () -> Unit
            tabContent[selectedTabIndex].content()
        }
    }
}


@Composable
private fun TabWithChats(
    chats: List<Chat>,
    onChatClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
) {
   // val scrollState = rememberLazyListState()

    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        if (chats.isEmpty()) {
            EmptyContent(
                icon = Icons.Default.ChatBubble,
                label = R.string.no_chats_all,
                modifier
            )
        } else {
            chats.forEach{ chat ->
                ChatRow(
                    chat = chat,
                    onClick = { onChatClick(chat) }
                )
            }
           /** Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                LazyColumn(state = scrollState,) {
                    items(chats, key = { it.chatId }) { chat ->
                        ChatRow(
                            chat = chat,
                            onClick = {}
                        )
                    }
                }
            }*/
        }
    }
}

@Composable
private fun TabWithArchives(
    archives: List<Chat>,
    //    onAssetClick: (Asset) -> Unit,
    modifier: Modifier = Modifier,
) {
    InterestsAdaptiveContentLayout(
        topPadding = 16.dp,
        modifier = tabContainerModifier.verticalScroll(rememberScrollState())
    ) {
        if (archives.isEmpty()) {
            EmptyContent(
                icon = Icons.Default.Archive,
                label = R.string.no_chats_all,
                modifier
            )
        } else {
            archives.forEach{ archive ->
                ChatRow(
                    chat = archive,
                    onClick = { /*TODO*/ }
                )
            }
        }
    }
}










/**
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
import com.kapirti.ira.R
import com.kapirti.ira.core.datastore.ChatIdRepository
import com.kapirti.ira.core.viewmodel.IncludeChatViewModel
import com.kapirti.ira.model.Chat

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatsScreen(
    chats: List<Chat>,
    contentPadding: PaddingValues,
    onChatClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
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
                onClick = { onChatClick(chat) }
            )
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
*/

/**

floatingActionButton = {
Button(onClick = chatsToArchive){
Text(stringResource(AppText.archive))
}
},


}
 */
