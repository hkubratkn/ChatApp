package com.kapirti.ira.soci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kapirti.ira.soci.ui.Bubble

class BubbleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatId = intent.data?.lastPathSegment?.toLongOrNull()
        setContent {
            Bubble(chatId = chatId!!)
        }
    }
}
