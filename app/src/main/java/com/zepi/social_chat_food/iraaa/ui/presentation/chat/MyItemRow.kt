package com.zepi.social_chat_food.iraaa.ui.presentation.chat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zepi.social_chat_food.iraaa.common.ext.getTimeFromTimestamp
import com.zepi.social_chat_food.model.ChatRow

@Composable
fun MyItemRow(chatRow: com.zepi.social_chat_food.model.ChatRow) {
    val ChatBubbleShape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Green),
            shape = ChatBubbleShape,
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
        ) {
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(16.dp)) {
                Column() {
                    Text(
                        text = chatRow.text,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall
                    )
                    chatRow.date?.let {
                        Text(
                            text = getTimeFromTimestamp(it.seconds),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                  /**  CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                  chatRow.date?.let {
                  Text(
                  text = getTimeFromTimestamp(it.seconds),
                  color = Color.White,
                  fontSize = 12.sp
                  )
                  }
                    }*/
                }
            }

        }
    }
}
