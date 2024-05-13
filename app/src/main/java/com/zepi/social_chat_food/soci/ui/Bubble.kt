package com.zepi.social_chat_food.soci.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.soci.ui.chat.ChatScreen
import com.zepi.social_chat_food.ui.theme.ZepiTheme

@Composable
fun Bubble(chatId: Long) {
    ZepiTheme {
        ChatScreen(
            chatId = chatId,
            foreground = false,
            onBackPressed = null,
            // TODO (donovanfm): Hook up camera button in the Bubble composable
            onCameraClick = {},
            // TODO (jolandaverhoef): Hook up play video button in the Bubble composable
            onVideoClick = {},
            // TODO (mayurikhin): Hook up camera button in the Bubble composable
            onPhotoPickerClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
