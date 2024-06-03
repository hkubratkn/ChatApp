package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chats

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRow(
    chat: Chat,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClickLabel = stringResource(AppText.long_click),
                onLongClick = onLongClick,
                onClick = onClick,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // This only supports DM for now.
        //   val contact = chat.attendees.first()
        NoSurfaceImage(imageUrl = chat.partnerPhoto,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "${chat.partnerName} ${chat.partnerSurname}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
            )
            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if(chat.unread == 0) "" else chat.unread.toString(),//"chat.chatWithLastMessage.timestamp.toReadableString()",
                fontSize = 14.sp,
            )
        }
    }
}
