package com.kapirti.ira.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kapirti.ira.R.string as AppText

@Composable
fun EmptyContent(
    icon: ImageVector,
    @StringRes label: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Text(stringResource(id = label))
    }
}

@Composable
fun EmptyContentChats(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Chat,
            contentDescription = stringResource(AppText.no_chats_all),
            modifier = Modifier.size(96.dp)
        )
        Text(stringResource(AppText.no_chats_all))
        Text(
            stringResource(AppText.start_new_conversation),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp).padding(top = 10.dp)
        )
    }
}
