package com.kapirti.pomodorotechnique_timemanagementmethod.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.R.drawable as AppIcon
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Work

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToProductivity: () -> Unit,
    navigateToTimeline: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToJob: () -> Unit,
    navigateToEmployee: () -> Unit,
    navigateToChats: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSubscriptions: () -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier) {
        PomodoroLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.timeline_title)) },
            icon = { Icon(Icons.Filled.Timeline, stringResource(id = AppText.timeline_title)) },
            selected = currentRoute == PomodoroDestinations.TIMELINE_ROUTE,
            onClick = { navigateToTimeline(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.home_title)) },
            icon = { Icon(Icons.Filled.Home, stringResource(id = AppText.home_title)) },
            selected = currentRoute == PomodoroDestinations.HOME_ROUTE,
            onClick = { navigateToHome(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.job_title)) },
            icon = { Icon(Icons.Default.Work, stringResource(id = AppText.job_title)) },
            selected = currentRoute == PomodoroDestinations.JOB_ROUTE,
            onClick = { navigateToJob(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.employee_title)) },
            icon = {
                Icon(
                    Icons.Default.SupervisorAccount,
                    stringResource(id = AppText.employee_title)
                )
            },
            selected = currentRoute == PomodoroDestinations.EMPLOYEE_ROUTE,
            onClick = { navigateToEmployee(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.productivity_title)) },
            icon = { Icon(Icons.Filled.Timer, stringResource(id = AppText.productivity_title)) },
            selected = currentRoute == PomodoroDestinations.PRODUCTIVITY_ROUTE,
            onClick = { navigateToProductivity(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.chats_title)) },
            icon = {
                Icon(
                    Icons.Default.ChatBubbleOutline,
                    stringResource(id = AppText.chats_title)
                )
            },
            selected = currentRoute == PomodoroDestinations.CHATS_ROUTE,
            onClick = { navigateToChats(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.profile_title)) },
            icon = {
                Icon(
                    Icons.Default.AccountCircle,
                    stringResource(id = AppText.profile_title)
                )
            },
            selected = currentRoute == PomodoroDestinations.PROFILE_ROUTE,
            onClick = { navigateToProfile(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.settings_title)) },
            icon = { Icon(Icons.Default.Settings, stringResource(id = AppText.settings_title)) },
            selected = currentRoute == PomodoroDestinations.SETTINGS_ROUTE,
            onClick = { navigateToSettings(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = AppText.subscriptions_title)) },
            icon = {
                Icon(
                    Icons.Default.Subscriptions,
                    stringResource(id = AppText.subscriptions_title)
                )
            },
            selected = currentRoute == PomodoroDestinations.SUBSCRIPTIONS_ROUTE,
            onClick = { navigateToSubscriptions(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun PomodoroLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            painterResource(AppIcon.icon),
            contentDescription = stringResource(id = AppText.cd_logo),
            modifier = Modifier.size(40.dp),
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(id = AppText.app_name),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
