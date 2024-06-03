package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chat.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DropdownContextMenu
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.contextMenu
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    user: User,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPressed: (() -> Unit)?,
    onTopBarClick: () -> Unit,
    options: List<String>,
    onActionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.clickable { onTopBarClick() }
            ) {
                SmallContactIcon(iconUri = user.photo, size = 32.dp)
                Text(text = "${user.name} ${user.surname}")
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (onBackPressed != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            }
        },
        actions = {
            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        },
    )
}

@Composable
fun SmallContactIcon(iconUri: String, size: Dp) {
    NoSurfaceImage(
        imageUrl = iconUri,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray),
    )
}


/**

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.common.composable.DropdownContextMenu
import com.zepi.social_chat_food.common.ext.contextMenu
import com.zepi.social_chat_food.common.ext.timeCustomFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelNameBar(
    user: com.zepi.social_chat_food.model.User,
    title: String,
    startAction: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    JetchatAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        startAction = startAction,
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user?.let {
                        if(it.online){
                            stringResource(id = AppText.online)
                        } else {
                            it.lastSeen?.let { itTime ->
                                timeCustomFormat(itTime.seconds)
                            }
                        }
                    } ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JetchatAppBar(
    startAction: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = actions,
        title = title,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            Box(modifier) {
                IconButton(onClick = startAction) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
    )
}*/
