package com.test.test.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.test.test.common.composable.AppIcons
import com.test.test.ui.TopLevelDestination.Companion.isTopLevel
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route


    @Serializable
    data object Home : Route

    @Serializable
    data object Chats : Route

    @Serializable
    data object Calls : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class Camera(val chatId: String) : Route

    @Serializable
    class UserProfile(val userId: String, val myId: String) : Route

    @Serializable
    //class SingleChat(val firstId: String, val secondId: String, val name: String) : Route
    class SingleChat(val roomId: String , val uriText: String? = null, val uriMimeType: String? = null) : Route
}

enum class TopLevelDestination(
    val route: Route,
    val label: String,
    val imageVector: ImageVector,
) {
    Home(
        route = Route.Home,
        label = "home",
        imageVector = AppIcons.Home,
    ),
    Chats(
        route = Route.Chats,
        label = "chats",
        imageVector = AppIcons.Home,
    ),
    Calls(
        route = Route.Calls,
        label = "calls",
        imageVector = AppIcons.Home,
    ),
    Settings(
        route = Route.Settings,
        label = "settings",
        imageVector = AppIcons.Home,
    ),
    ;

    companion object {
        val START_DESTINATION = Home

        fun fromNavDestination(destination: NavDestination?): TopLevelDestination {
            return entries.find { dest ->
                destination?.hierarchy?.any {
                    it.hasRoute(dest.route::class)
                } == true
            } ?: START_DESTINATION
        }

        fun NavDestination.isTopLevel(): Boolean {
            return entries.any {
                hasRoute(it.route::class)
            }
        }
    }
}

private fun calculateNavigationLayoutType(
    destination: NavDestination?,
    defaultLayoutType: NavigationSuiteType,
): NavigationSuiteType {
    return when {
        destination == null -> defaultLayoutType
        // Never show navigation UI on Camera.
        destination.hasRoute<Route.Camera>() -> NavigationSuiteType.None
        // Top level destinations can show any layout type.
        destination.isTopLevel() -> defaultLayoutType
        // Every other destination goes through a ChatThread. Hide the bottom nav bar
        // since it interferes with composing chat messages.
        defaultLayoutType == NavigationSuiteType.NavigationBar -> NavigationSuiteType.None
        else -> defaultLayoutType
    }
}

@Composable
fun SocialiteNavSuite(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    val topLevelDestination = TopLevelDestination.fromNavDestination(destination)
    val defaultLayoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
        currentWindowAdaptiveInfo(),
    )
    val layoutType = calculateNavigationLayoutType(destination, defaultLayoutType)

    NavigationSuiteScaffold(
        modifier = modifier,
        layoutType = layoutType,
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach {
                val isSelected = it == topLevelDestination
                item(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = it.imageVector,
                            contentDescription = it.label,
                        )
                    },
                    label = {
                        Text(text = it.label)
                    },
                    alwaysShowLabel = false,
                )
            }
        },
    ) {
        content()
    }
}
