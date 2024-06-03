package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.home
/**
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.EmptyContent
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.LoadingContent
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User


@Composable
fun HomeScreen(
    loading: Boolean,
    items: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User>,
    refresh: () -> Unit,
    onUserClick: (com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LoadingContent(
        loading = loading,
        empty = items.isEmpty() && !loading,
        emptyContent = {
            EmptyContent(
                icon = Icons.Default.Person,
                label = R.string.no_users_all,
                modifier
            )
        },
        onRefresh = refresh
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            LazyColumn(state = scrollState,) {
                items(items, key = { it.uid }) { userItem ->
                    UserItem(
                        user = userItem,
                        onClick = {
                            onUserClick(userItem)
                        }
                    )
                }
            }
        }
    }
}





/**
import androidx.compose.animation.AnimatedVisibility
i


@Composable
@ExperimentalMaterialApi
fun HomeScreen(
openScreen: (String) -> Unit,
usersToUserProfileViewModel: UsersToUserProfileViewModel,
modifier: Modifier = Modifier,
viewModel: HomeViewModel = hiltViewModel(),
chatsViewModel: ChatsViewModel = hiltViewModel(),
scaffoldState: ScaffoldState = rememberScaffoldState(),
nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
val uid = viewModel.currentUserUid
val users = viewModel.users.collectAsStateWithLifecycle(emptyList())
val usersBlock = viewModel.userBlock.collectAsStateWithLifecycle(emptyList())
val chatsUiState by chatsViewModel.uiState.collectAsStateWithLifecycle()

val scrollState = rememberLazyListState()
val scope = rememberCoroutineScope()

BoxWithConstraints(
modifier = modifier
.fillMaxSize()
.nestedScroll(nestedScrollInteropConnection)
.systemBarsPadding()
) {
Scaffold(
scaffoldState = scaffoldState,
topBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
modifier = modifier.fillMaxSize(),
) { paddingValues ->
HomeContent(
loading = chatsUiState.isLoading,
uid = uid,
users = users.value,
chats = chatsUiState.items,
usersBlock = usersBlock.value,
onRefresh = viewModel::refresh,
onUserClick = {
usersToUserProfileViewModel.addUser(it)
openScreen(USER_PROFILE_SCREEN)
},
modifier = Modifier.padding(paddingValues),
scrollState = scrollState
)
}


val jumpThreshold = with(LocalDensity.current) { JumpToTopThreshold.toPx() }
val jumpToTopButtonEnabled by remember {
derivedStateOf {
scrollState.firstVisibleItemIndex != 0 ||
scrollState.firstVisibleItemScrollOffset > jumpThreshold
}
}
JumpToButton(
text = AppText.jump_top,
icon = Icons.Default.ArrowUpward,
enabled = jumpToTopButtonEnabled,
onClicked = {
scope.launch {
scrollState.animateScrollToItem(0)
}
},
modifier = Modifier.align(Alignment.BottomCenter)
)
}
LaunchedEffect(viewModel) { viewModel.initialize() }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
loading: Boolean,
uid : String,
users: List<User>,
usersBlock: List<Block>,
chats: List<Chat>,
onRefresh: () -> Unit,
onUserClick: (User) -> Unit,
modifier: Modifier = Modifier,
scrollState: LazyListState
) {
LoadingContent(
loading = loading,
empty = users.isEmpty() && !loading,
emptyContent = { EmptyContent(icon = Icons.Default.Person, label = AppText.no_users_all, modifier) },
onRefresh = onRefresh
) {
val timestamp = Timestamp.now()
val missList = arrayListOf(uid)

for (mess in chats) {
missList.add(mess.partnerUid.toString())
}
for (mek in usersBlock) {
missList.add(mek.uid.toString())
}

Column(
modifier = modifier
.fillMaxSize()
.padding(horizontal = dimensionResource(id = AppDimen.horizontal_margin))
) {
LazyColumn(state = scrollState,) {
items(users, key = { it.uid }) { userItem ->
val usersUid = userItem.uid
val result = usersUid in missList

if (result) {
Log.d("TAG", usersUid.toString())
} else {
UserItem(
user = userItem,
timestamp = timestamp,
onUserClick = {
onUserClick(userItem)
}
)
}
}
}
}
}
}

private val JumpToTopThreshold = 56.dp*/
/**
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.Timestamp
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.EmptyContent
import com.kapirti.ira.common.composable.JumpToButton
import com.kapirti.ira.common.composable.LoadingContent
import com.kapirti.ira.core.constants.Constants.ADS_HOME_BANNER_ID
import com.kapirti.ira.core.constants.Constants.USER_PROFILE_SCREEN
import com.kapirti.ira.core.view_model.UsersToUserProfileViewModel
import com.kapirti.ira.model.Block
import com.kapirti.ira.model.Chat
import com.kapirti.ira.model.User
import com.kapirti.ira.ui.presentation.chats.ChatsViewModel
import com.kapirti.ira.R.string as AppText
import com.kapirti.ira.R.dimen as AppDimen
import kotlinx.coroutines.launch


@Composable
@ExperimentalMaterialApi
fun HomeScreen(
openScreen: (String) -> Unit,
usersToUserProfileViewModel: UsersToUserProfileViewModel,
modifier: Modifier = Modifier,
viewModel: HomeViewModel = hiltViewModel(),
chatsViewModel: ChatsViewModel = hiltViewModel(),
scaffoldState: ScaffoldState = rememberScaffoldState(),
nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
val uid = viewModel.currentUserUid
val users = viewModel.users.collectAsStateWithLifecycle(emptyList())
val usersBlock = viewModel.userBlock.collectAsStateWithLifecycle(emptyList())
val chatsUiState by chatsViewModel.uiState.collectAsStateWithLifecycle()

val scrollState = rememberLazyListState()
val scope = rememberCoroutineScope()

BoxWithConstraints(
modifier = modifier
.fillMaxSize()
.nestedScroll(nestedScrollInteropConnection)
.systemBarsPadding()
) {
Scaffold(
scaffoldState = scaffoldState,
topBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
modifier = modifier.fillMaxSize(),
) { paddingValues ->
HomeContent(
loading = chatsUiState.isLoading,
uid = uid,
users = users.value,
chats = chatsUiState.items,
usersBlock = usersBlock.value,
onRefresh = viewModel::refresh,
onUserClick = {
usersToUserProfileViewModel.addUser(it)
openScreen(USER_PROFILE_SCREEN)
},
modifier = Modifier.padding(paddingValues),
scrollState = scrollState
)
}


val jumpThreshold = with(LocalDensity.current) { JumpToTopThreshold.toPx() }
val jumpToTopButtonEnabled by remember {
derivedStateOf {
scrollState.firstVisibleItemIndex != 0 ||
scrollState.firstVisibleItemScrollOffset > jumpThreshold
}
}
JumpToButton(
text = AppText.jump_top,
icon = Icons.Default.ArrowUpward,
enabled = jumpToTopButtonEnabled,
onClicked = {
scope.launch {
scrollState.animateScrollToItem(0)
}
},
modifier = Modifier.align(Alignment.BottomCenter)
)
}
LaunchedEffect(viewModel) { viewModel.initialize() }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
loading: Boolean,
uid : String,
users: List<User>,
usersBlock: List<Block>,
chats: List<Chat>,
onRefresh: () -> Unit,
onUserClick: (User) -> Unit,
modifier: Modifier = Modifier,
scrollState: LazyListState
) {
LoadingContent(
loading = loading,
empty = users.isEmpty() && !loading,
emptyContent = { EmptyContent(icon = Icons.Default.Person, label = AppText.no_users_all, modifier) },
onRefresh = onRefresh
) {
val timestamp = Timestamp.now()
val missList = arrayListOf(uid)

for (mess in chats) {
missList.add(mess.partnerUid.toString())
}
for (mek in usersBlock) {
missList.add(mek.uid.toString())
}

Column(
modifier = modifier
.fillMaxSize()
.padding(horizontal = dimensionResource(id = AppDimen.horizontal_margin))
) {
LazyColumn(state = scrollState,) {
items(users, key = { it.uid }) { userItem ->
val usersUid = userItem.uid
val result = usersUid in missList

if (result) {
Log.d("TAG", usersUid.toString())
} else {
UserItem(
user = userItem,
timestamp = timestamp,
onUserClick = {
onUserClick(userItem)
}
)
}
}
}
}
}
}

private val JumpToTopThreshold = 56.dp*/


/**
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.Timestamp
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.common.composable.EmptyContent
import com.kapirti.ira.common.composable.JumpToButton
import com.kapirti.ira.common.composable.LoadingContent
import com.kapirti.ira.core.constants.Constants.ADS_HOME_BANNER_ID
import com.kapirti.ira.core.constants.Constants.USER_PROFILE_SCREEN
import com.kapirti.ira.core.view_model.UsersToUserProfileViewModel
import com.kapirti.ira.model.Block
import com.kapirti.ira.model.Chat
import com.kapirti.ira.model.User
import com.kapirti.ira.ui.presentation.chats.ChatsViewModel
import com.kapirti.ira.R.string as AppText
import com.kapirti.ira.R.dimen as AppDimen
import kotlinx.coroutines.launch


@Composable
@ExperimentalMaterialApi
fun HomeScreen(
openScreen: (String) -> Unit,
usersToUserProfileViewModel: UsersToUserProfileViewModel,
modifier: Modifier = Modifier,
viewModel: HomeViewModel = hiltViewModel(),
chatsViewModel: ChatsViewModel = hiltViewModel(),
scaffoldState: ScaffoldState = rememberScaffoldState(),
nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
val uid = viewModel.currentUserUid
val users = viewModel.users.collectAsStateWithLifecycle(emptyList())
val usersBlock = viewModel.userBlock.collectAsStateWithLifecycle(emptyList())
val chatsUiState by chatsViewModel.uiState.collectAsStateWithLifecycle()

val scrollState = rememberLazyListState()
val scope = rememberCoroutineScope()

BoxWithConstraints(
modifier = modifier
.fillMaxSize()
.nestedScroll(nestedScrollInteropConnection)
.systemBarsPadding()
) {
Scaffold(
scaffoldState = scaffoldState,
topBar = { AdsBannerToolbar(ads = ADS_HOME_BANNER_ID) },
modifier = modifier.fillMaxSize(),
) { paddingValues ->
HomeContent(
loading = chatsUiState.isLoading,
uid = uid,
users = users.value,
chats = chatsUiState.items,
usersBlock = usersBlock.value,
onRefresh = viewModel::refresh,
onUserClick = {
usersToUserProfileViewModel.addUser(it)
openScreen(USER_PROFILE_SCREEN)
},
modifier = Modifier.padding(paddingValues),
scrollState = scrollState
)
}


val jumpThreshold = with(LocalDensity.current) { JumpToTopThreshold.toPx() }
val jumpToTopButtonEnabled by remember {
derivedStateOf {
scrollState.firstVisibleItemIndex != 0 ||
scrollState.firstVisibleItemScrollOffset > jumpThreshold
}
}
JumpToButton(
text = AppText.jump_top,
icon = Icons.Default.ArrowUpward,
enabled = jumpToTopButtonEnabled,
onClicked = {
scope.launch {
scrollState.animateScrollToItem(0)
}
},
modifier = Modifier.align(Alignment.BottomCenter)
)
}
LaunchedEffect(viewModel) { viewModel.initialize() }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
loading: Boolean,
uid : String,
users: List<User>,
usersBlock: List<Block>,
chats: List<Chat>,
onRefresh: () -> Unit,
onUserClick: (User) -> Unit,
modifier: Modifier = Modifier,
scrollState: LazyListState
) {
LoadingContent(
loading = loading,
empty = users.isEmpty() && !loading,
emptyContent = { EmptyContent(icon = Icons.Default.Person, label = AppText.no_users_all, modifier) },
onRefresh = onRefresh
) {
val timestamp = Timestamp.now()
val missList = arrayListOf(uid)

for (mess in chats) {
missList.add(mess.partnerUid.toString())
}
for (mek in usersBlock) {
missList.add(mek.uid.toString())
}

Column(
modifier = modifier
.fillMaxSize()
.padding(horizontal = dimensionResource(id = AppDimen.horizontal_margin))
) {
LazyColumn(state = scrollState,) {
items(users, key = { it.uid }) { userItem ->
val usersUid = userItem.uid
val result = usersUid in missList

if (result) {
Log.d("TAG", usersUid.toString())
} else {
UserItem(
user = userItem,
timestamp = timestamp,
onUserClick = {
onUserClick(userItem)
}
)
}
}
}
}
}
}

private val JumpToTopThreshold = 56.dp*/


























/**   val uiState by viewModel.uiState.collectAsStateWithLifecycle()
val topAsset by viewModel.topAsset.collectAsStateWithLifecycle(initialValue = emptyList())

AssetContent(
loading = uiState.isLoading,
topAsset = topAsset,
assets = uiState.items,
onRefresh = viewModel::refresh,
onAssetClick = {
includeAssetViewModel.addAsset(it)
navigateToAssetDetail()
},
onToggleFavorite = {
viewModel.onToggleFavorite(it)
},
modifier = modifier,
scrollState = scrollState
)
}

@Composable
private fun AssetContent(
loading: Boolean,
topAsset: List<Asset>,
assets: List<Asset>,
onRefresh: () -> Unit,
onAssetClick: (Asset) -> Unit,
onToggleFavorite: (Asset) -> Unit,
scrollState: LazyListState,
modifier: Modifier = Modifier,
) {
LoadingContent(
loading = loading,
empty = assets.isEmpty() && !loading,
emptyContent = { EmptyContent(R.string.assets_not_found, Icons.Default.DirectionsCar, modifier) },
onRefresh = onRefresh
) {
Column(
modifier = modifier
.fillMaxSize()
.padding(horizontal = 1.dp)
) {
LazyColumn(state = scrollState) {
items (topAsset, key = { it.uid}){ top ->
HomeTopItem(
topAsset = top,
onAssetClick = onAssetClick,
)
}
items(assets, key = { it.uid }) { asset ->
HomeItem(
asset = asset,
onAssetClick = onAssetClick,
onToggleFavorite = onToggleFavorite
//  navigateToArticle = {},
// isFavorite = false,
)
/**  HomeItem(
asset = assetItem,
onAssetClick = onAssetClick
)*/
}
}
}
}
}*/
*/
