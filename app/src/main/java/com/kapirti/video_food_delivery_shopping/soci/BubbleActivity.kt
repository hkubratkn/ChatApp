package com.kapirti.video_food_delivery_shopping.soci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kapirti.video_food_delivery_shopping.soci.ui.Bubble

class BubbleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatId = intent.data?.lastPathSegment?.toLongOrNull()
        setContent {
            Bubble(chatId = chatId!!)
        }
    }
}
