package com.kapirti.ira.ui.presentation.blockusers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kapirti.ira.common.composable.NoSurfaceImage
import com.kapirti.ira.common.ext.toReadableString
import com.kapirti.ira.model.Block

@Composable
fun BlockUsersScreen(
    blockUsers: List<Block>,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(state = scrollState,) {
            items(blockUsers, key = { it.uid }) { blockUser ->
                BlockUserRow(block = blockUser)
            }
        }
    }
}

@Composable
private fun BlockUserRow(
    block: Block,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // This only supports DM for now.
        //   val contact = chat.attendees.first()
        NoSurfaceImage(imageUrl = block.photo,
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
                text = "${block.name} ${block.surname}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
            )
            Text(
                text = block.date?.let { it.seconds.toReadableString() }  ?: "",
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
                text = "36",//"chat.chatWithLastMessage.timestamp.toReadableString()",
                fontSize = 14.sp,
            )
        }
    }
}
