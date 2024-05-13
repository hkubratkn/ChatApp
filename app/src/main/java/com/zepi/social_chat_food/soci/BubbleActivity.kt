package com.zepi.social_chat_food.soci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zepi.social_chat_food.soci.ui.Bubble

class BubbleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatId = intent.data?.lastPathSegment?.toLongOrNull()
        setContent {
            Bubble(chatId = chatId!!)
        }
    }
}
