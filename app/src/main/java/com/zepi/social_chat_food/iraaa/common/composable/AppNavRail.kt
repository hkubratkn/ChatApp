package com.zepi.social_chat_food.iraaa.common.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.R.drawable as AppIcon
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.ui.ZepiDestinations

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = {
            Icon(
                painterResource(AppIcon.ic_send),
                null,
                Modifier.padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == ZepiDestinations.HOME_ROUTE,
            onClick = navigateToHome,
            icon = { Icon(Icons.Filled.Home, stringResource(AppText.home_title)) },
            label = { Text(stringResource(AppText.home_title)) },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            selected = currentRoute == ZepiDestinations.SETTINGS_ROUTE,
            onClick = navigateToSettings,
            icon = { Icon(Icons.Filled.ListAlt, stringResource(AppText.settings_title)) },
            label = { Text(stringResource(AppText.settings_title)) },
            alwaysShowLabel = false
        )
        Spacer(Modifier.weight(1f))
    }
}
