package com.kapirti.video_food_delivery_shopping.iraaa.ui.presentation.chat.exist
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
    options: List<String>,
    startAction: () -> Unit,
    onTopBarClick: () -> Unit,
    onActionClick: (String) -> Unit,
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
                modifier = Modifier.clickable { onTopBarClick() }
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
        actions = {
            DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
        }
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
}
*/
