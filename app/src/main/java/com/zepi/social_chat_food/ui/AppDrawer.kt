package com.zepi.social_chat_food.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.R.drawable as AppIcon

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToChats: () -> Unit,
    navigateToSubscriptions: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier) {
        QChatLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.home_title)) },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = currentRoute == ZepiDestinations.HOME_ROUTE,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.chats_title)) },
            icon = { Icon(Icons.Default.ChatBubble, null)},
            selected = currentRoute == ZepiDestinations.CHATS_ROUTE,
            onClick = { navigateToChats(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.profile_title)) },
            icon = { Icon(Icons.Default.AccountCircle, null)},
            selected = currentRoute == ZepiDestinations.PROFILE_ROUTE,
            onClick = { navigateToProfile(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.settings_title)) },
            icon = { Icon(Icons.Default.Settings, null)},
            selected = currentRoute == ZepiDestinations.SETTINGS_ROUTE,
            onClick = { navigateToSettings(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.subscriptions_title)) },
            icon = { Icon(Icons.Default.Subscriptions, null)},
            selected = currentRoute == ZepiDestinations.SUBSCRIPTIONS_ROUTE,
            onClick = { navigateToSubscriptions(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun QChatLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            painterResource(AppIcon.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(id = AppText.app_name),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
