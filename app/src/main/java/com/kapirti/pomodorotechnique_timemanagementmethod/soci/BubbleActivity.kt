package com.kapirti.pomodorotechnique_timemanagementmethod.soci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.Bubble

class BubbleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatId = intent.data?.lastPathSegment?.toLongOrNull()
        setContent {
            Bubble(chatId = chatId!!)
        }
    }
}
