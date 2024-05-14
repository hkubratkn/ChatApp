package com.zepi.social_chat_food.iraaa.ui.presentation.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.iraaa.common.composable.NoSurfaceImage
import com.zepi.social_chat_food.model.Chat

@Composable
fun ChatItem(
    chat: com.zepi.social_chat_food.model.Chat,
    onChatsClick: (com.zepi.social_chat_food.model.Chat) -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .padding(8.dp, 0.dp, 8.dp, 8.dp)
            .clickable {
                onChatsClick(chat)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            NoSurfaceImage(
                imageUrl = chat.partnerPhoto,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .size(50.dp),
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${chat.partnerName} ${chat.partnerSurname}", style = MaterialTheme.typography.titleSmall, color = Color.Cyan)
            }

            if(chat.unread != 0){
                Text(text = chat.unread.toString(), color = Color.Green, modifier = Modifier.padding(end = 5.dp))
            }
        }
    }
}
