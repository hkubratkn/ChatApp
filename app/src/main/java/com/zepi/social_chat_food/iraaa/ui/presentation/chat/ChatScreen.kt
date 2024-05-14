@file:OptIn(ExperimentalMaterial3Api::class)

package com.zepi.social_chat_food.iraaa.ui.presentation.chat

import androidx.compose.material3.ExperimentalMaterial3Api

/**
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.common.EmptyContent
import com.zepi.social_chat_food.iraaa.common.composable.DialogCancelButton
import com.zepi.social_chat_food.iraaa.common.composable.DialogConfirmButton
import com.zepi.social_chat_food.iraaa.common.composable.JumpToButton
import com.zepi.social_chat_food.iraaa.common.composable.LoadingContent
import com.zepi.social_chat_food.iraaa.common.ext.toolbarActions
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.model.ChatRow
import com.zepi.social_chat_food.iraaa.ui.presentation.chat.exist.ChannelNameBar
import com.zepi.social_chat_food.iraaa.ui.presentation.chat.exist.UserInput


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ChatScreen(
    popUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    includeChatViewModel: IncludeChatViewModel,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val options by viewModel.options
    val chat = includeChatViewModel.chat

    LaunchedEffect(Unit) {
        viewModel.initialize(
            chatId = chat?.let { it.chatId } ?: "",
            uid = chat?.let { it.partnerUid } ?: ""
        )
    }

    val chatRow = viewModel.chatRow.collectAsStateWithLifecycle(emptyList())
    val unread = viewModel.unread.collectAsStateWithLifecycle(0)
    val user = viewModel.user.collectAsStateWithLifecycle()
    val profile = viewModel.profile.collectAsState(initial = null)

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()


    val partnerUid = chat?.let { it.partnerUid } ?: ""
    val partnerPhoto = chat?.let { it.partnerPhoto } ?: ""
    val partnerSurname = chat?.let { it.partnerSurname } ?: ""
    val partnerName = chat?.let { it.partnerName } ?: ""

    val uid = viewModel.uid
    val name = profile.value?.let { it.namedb } ?: ""
    val surname = profile.value?.let { it.surnamedb } ?: ""
    val photo = profile.value?.let { it.photodb } ?: ""

    Surface(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {

                Messages(
                    myUid = uid,
                    messages = chatRow.value,
                    loading = viewModel.isLoading,
                    onRefresh = viewModel::refresh,
                    modifier = Modifier.weight(1f),
                    scrollState = scrollState
                )

                UserInput(
                    onMessageSent = { content ->
                        viewModel.onDoneClick(
                            chatId = chat?.let { it.chatId } ?: "",
                            text = content,
                            token = user.value?.let { it.token } ?: "",
                            userNameSurname = "${name} ${surname}",
                            partnerUid = partnerUid,
                            unread = unread.value
                        )
                    },
                    resetScroll = {
                        scope.launch {
                            scrollState.scrollToItem(0)
                        }
                    },
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding(),
                )
            }

            ChannelNameBar(
                user = user.value,
                title = chat?.let { "${it.partnerName} ${it.partnerSurname}" } ?: "",
                options = options,
                onActionClick = { action -> viewModel.onChatActionClick(action) },
                onTopBarClick = {
                    showInterstialAd()
                    includeUserUidViewModel.addUserUid(chat?.let { it.partnerUid } ?: "")
                    navigateUserProfile()
                                },
                startAction = { popUp() },
                modifier = Modifier.toolbarActions(),
                scrollBehavior = scrollBehavior,
            )
        }
    }

    LaunchedEffect(viewModel) { viewModel.loadChatOptions() }

    if (viewModel.showBlockDialog.value) {
        AlertDialog(
            title = { Text(stringResource(AppText.block)) },
            text = { Text(stringResource(AppText.block_user)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { viewModel.showBlockDialog.value = false } },
            confirmButton = {
                DialogConfirmButton(AppText.block) {
                    viewModel.onBlockButtonClick(popUp, chatId = chat?.let { it.chatId } ?: "",
                        name = name, surname = surname, photo = photo,
                        partnerUid = partnerUid, partnerPhoto = partnerPhoto,
                        partnerSurname = partnerSurname, partnerName = partnerName
                    )
                    viewModel.showBlockDialog.value = false
                }
            },
            onDismissRequest = { viewModel.showBlockDialog.value = false }
        )
    }
    if (viewModel.showReportDialog.value) {
        AlertDialog(
            title = { Text(stringResource(AppText.report)) },
            text = { Text(stringResource(AppText.report_user)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { viewModel.showReportDialog.value = false } },
            confirmButton = {
                DialogConfirmButton(AppText.report) {
                    viewModel.onReportButtonClick(popUp, chatId = chat?.let { it.chatId } ?: "",
                        name = name, surname = surname, photo = photo,
                        partnerUid = partnerUid, partnerPhoto = partnerPhoto,
                        partnerSurname = partnerSurname, partnerName = partnerName
                    )
                    viewModel.showReportDialog.value = false
                }
            },
            onDismissRequest = { viewModel.showReportDialog.value = false }
        )
    }
}


@Composable
private fun Messages(
    myUid: String,
    messages: List<com.zepi.social_chat_food.model.ChatRow>,
    scrollState: LazyListState,
    loading: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        MessagesContent(
            loading = loading,
            myUid = myUid,
            messages = messages,
            onRefresh = onRefresh,
            modifier = modifier,
            scrollState = scrollState
        )

        val jumpThreshold = with(LocalDensity.current) {
            JumpToBottomThreshold.toPx()
        }
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToButton(
            text = AppText.jump_bottom,
            icon = Icons.Filled.ArrowDownward,
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun MessagesContent(
    loading: Boolean,
    myUid: String,
    messages: List<com.zepi.social_chat_food.model.ChatRow>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState
) {
    LoadingContent(
        loading = loading,
        empty = messages.isEmpty() && !loading,
        emptyContent = { EmptyContent(icon = Icons.Default.Chat, label = AppText.no_messages_all, modifier) },
        onRefresh = onRefresh
    ) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            contentPadding = WindowInsets.statusBars.add(WindowInsets(top = 90.dp)).asPaddingValues(),
            modifier = Modifier
//                .testTag(ConversationTestTag)
                .fillMaxSize()
        ) {
            items(messages){ item ->
                if(item.who == myUid){
                    MyItemRow(item)
                } else {
                    OtherItemRow(item)
                }
            }
        }
    }
}

const val ConversationTestTag = "ConversationTestTag"
private val JumpToBottomThreshold = 56.dp
private fun ScrollState.atBottom(): Boolean = value == 0
*/
