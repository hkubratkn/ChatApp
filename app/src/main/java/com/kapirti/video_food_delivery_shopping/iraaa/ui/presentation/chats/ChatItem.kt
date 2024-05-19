/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.video_food_delivery_shopping.iraaa.ui.presentation.chats

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
import com.kapirti.video_food_delivery_shopping.common.composable.NoSurfaceImage
import com.kapirti.video_food_delivery_shopping.model.Chat

@Composable
fun ChatItem(
    chat: Chat,
    onChatsClick: (Chat) -> Unit
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

          //  if(chat.unread != 0){
            //    Text(text = chat.unread.toString(), color = Color.Green, modifier = Modifier.padding(end = 5.dp))
           // }
        }
    }
}
