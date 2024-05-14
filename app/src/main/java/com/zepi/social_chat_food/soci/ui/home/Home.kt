package com.zepi.social_chat_food.soci.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zepi.social_chat_food.R
import com.zepi.social_chat_food.soci.ui.home.timeline.Timeline

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
fun Home(
    onChatClicked: (chatId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeContent(modifier, onChatClicked)
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    onChatClicked: (chatId: Long) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { HomeAppBar(title = "stringResource(currentDestination.label)") },
    ) { innerPadding ->
        val navController = rememberNavController()
        HomeBackground(modifier = Modifier.fillMaxSize())


        val viewModel: HomeViewModel = hiltViewModel()
        val chats by viewModel.chats.collectAsStateWithLifecycle()
        ChatList(
            chats = chats,
            contentPadding = innerPadding,
            onChatClicked = onChatClicked,
            modifier = modifier,
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}




/**

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zepi.social_chat_food.R
import com.zepi.social_chat_food.soci.ui.AnimationConstants
import com.zepi.social_chat_food.soci.ui.home.timeline.Timeline

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
fun Home(
    onChatClicked: (chatId: Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var currentDestination by rememberSaveable { mutableStateOf(Destination.Chats) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            for (destination in Destination.entries) {
                val selected = currentDestination.route == destination.route
                item(
                    selected = selected,
                    onClick = { currentDestination = destination },
                    icon = {
                        Icon(
                            imageVector = destination.imageVector,
                            contentDescription = stringResource(destination.label),
                        )
                    },
                    label = {
                        Text(text = stringResource(destination.label))
                    },
                    alwaysShowLabel = false,
                )
            }
        },
    ) { HomeContent(currentDestination, modifier, onChatClicked) }
}

@Composable
private fun HomeContent(
    currentDestination: Destination,
    modifier: Modifier,
    onChatClicked: (chatId: Long) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { HomeAppBar(title = stringResource(currentDestination.label)) },
    ) { innerPadding ->
        val navController = rememberNavController()
        HomeBackground(modifier = Modifier.fillMaxSize())
        NavHost(
            navController = navController,
            startDestination = currentDestination.route,
            modifier = modifier,
        ) {
            composable(
                route = Destination.Timeline.route,
                enterTransition = { AnimationConstants.enterTransition },
                exitTransition = { AnimationConstants.exitTransition },
            ) {
                Timeline(
                    contentPadding = innerPadding,
                    modifier = modifier,
                )
            }
            composable(
                route = Destination.Chats.route,
                enterTransition = { AnimationConstants.enterTransition },
                exitTransition = { AnimationConstants.exitTransition },
            ) {
                val viewModel: HomeViewModel = hiltViewModel()
                val chats by viewModel.chats.collectAsStateWithLifecycle()
                ChatList(
                    chats = chats,
                    contentPadding = innerPadding,
                    onChatClicked = onChatClicked,
                    modifier = modifier,
                )
            }
            composable(
                route = Destination.Settings.route,
                enterTransition = { AnimationConstants.enterTransition },
                exitTransition = { AnimationConstants.exitTransition },
            ) {
                Settings(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

private enum class Destination(
    val route: String,
    @StringRes val label: Int,
    val imageVector: ImageVector,
) {
    Timeline(
        route = "timeline",
        label = R.string.timeline,
        imageVector = Icons.Outlined.VideoLibrary,
    ),
    Chats(
        route = "chats",
        label = R.string.chats,
        imageVector = Icons.Outlined.ChatBubbleOutline,
    ),
    Settings(
        route = "settings",
        label = R.string.settings,
        imageVector = Icons.Outlined.Settings,
    ),
}
*/
