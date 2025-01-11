package com.test.test.ui.presentation.chats

import androidx.compose.runtime.Composable
import com.test.test.ui.presentation.conversations.ChatList

@Composable
fun ChatsRoute(
    onChatClicked: (String) -> Unit
) {
    ChatList(
        onChatClicked = onChatClicked
    )
}
