package com.zepi.social_chat_food.iraaa.ui.presentation.userprofile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Timestamp
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zepi.social_chat_food.R.string as AppText
import kotlin.math.max
import kotlin.math.min


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.BasicDivider
import com.zepi.social_chat_food.iraaa.common.composable.LoadingContent
import com.zepi.social_chat_food.iraaa.common.composable.QChatSurface
import com.zepi.social_chat_food.iraaa.common.composable.SurfaceImage
import com.zepi.social_chat_food.iraaa.common.composable.arrowBackIcon
import com.zepi.social_chat_food.iraaa.common.ext.calculateBirthday
import com.zepi.social_chat_food.iraaa.common.ext.timeCustomFormat
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds.ADS_USER_PROFILE_BANNER_ID
import com.zepi.social_chat_food.iraaa.core.constants.ConsGender.MALE
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel
import com.zepi.social_chat_food.iraaa.model.Chat
import com.zepi.social_chat_food.iraaa.model.User
import com.zepi.social_chat_food.iraaa.model.UserPhotos
import com.zepi.social_chat_food.iraaa.ui.presentation.chats.ChatsUiState
import com.zepi.social_chat_food.iraaa.ui.presentation.chats.ChatsViewModel

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)


@Composable
internal fun UserProfileRoute(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UserProfileScreen(
        popUpScreen = popUpScreen,
        onChatNopeClick = onChatNopeClick,
        onChatExistClick = onChatExistClick,
        onLoginClick = onLoginClick,
        showInterstitialAds = showInterstitialAds,
        includeUserUidViewModel = includeUserUidViewModel,
        uiState = uiState,
        onRefresh = viewModel::refresh,
    )
}

@Composable
private fun UserProfileScreen(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    uiState: ChatsUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    UserProfileContent(
        popUpScreen = popUpScreen,
        onChatNopeClick = onChatNopeClick,
        onChatExistClick = onChatExistClick,
        onLoginClick = onLoginClick,
        showInterstitialAds = showInterstitialAds,
        includeUserUidViewModel = includeUserUidViewModel,
        loading = uiState.isLoading,
        chats = uiState.items,
        onRefresh = onRefresh,
        modifier = modifier,
    )
}

@Composable
private fun UserProfileContent(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    loading: Boolean,
    chats: List<Chat>,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadingContent(
        loading = loading,
        empty = chats.isEmpty() && !loading,
        emptyContent = {
            UserProfileBody(
                popUpScreen = popUpScreen,
                onChatNopeClick = onChatNopeClick,
                onChatExistClick = onChatExistClick,
                onLoginClick = onLoginClick,
                showInterstialAd = showInterstitialAds,
                chats = emptyList(),
                includeUserUidViewModel = includeUserUidViewModel,
                modifier = modifier
            )
        },
        onRefresh = onRefresh
    ) {
        UserProfileBody(
            popUpScreen = popUpScreen,
            onChatNopeClick = onChatNopeClick,
            onChatExistClick = onChatExistClick,
            onLoginClick = onLoginClick,
            showInterstialAd = showInterstitialAds,
            chats = chats,
            includeUserUidViewModel = includeUserUidViewModel,
            modifier = modifier
        )
    }
}


@Composable
private fun UserProfileBody(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    showInterstialAd: () -> Unit,
    chats: List<Chat>,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            userUid = includeUserUidViewModel.userUid ?: "",
        )
    }

    val user = viewModel.user.collectAsStateWithLifecycle()
    val userPhotos = viewModel.userPhotos.collectAsStateWithLifecycle()

    val missList = arrayListOf(viewModel.uid)
    for (mek in chats) {
        missList.add(mek.partnerUid.toString())
    }

    Scaffold(
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Red)
                    .systemBarsPadding(),
                onClick = {
                    showInterstialAd()
                    if (viewModel.hasUser) {
                        val result = user.value.uid in missList

                        if(result){
                            onChatExistClick()
                        } else {
                            onChatNopeClick()
                        }
                    } else {
                        onLoginClick()
                    }
                }
            ) {
                Icon(Icons.Default.ChatBubble, null)
            }
        },
        modifier = modifier.systemBarsPadding()
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollInteropConnection)
                .systemBarsPadding()
                .padding(innerPadding)
        ) {
            Box(Modifier.fillMaxSize()) {
                val scroll = rememberScrollState(0)
                Header(user.value.gender)
                Body(user = user.value, photos = userPhotos.value ,scroll = scroll)
                Title(user.value) { scroll.value }
                Image(user.value.photo) { scroll.value }
                Up(popUpScreen)
                CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}
/**

@Composable
private fun PhotosContent(
    loading: Boolean,
    items: List<UserPhotos>,
    onRefresh: () -> Unit,
    onAssetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoadingContent(
        loading = loading,
        empty = items.isEmpty() && !loading,
        emptyContent = {
            EmptyContent(
                icon = Icons.Default.Add,
                label = R.string.user_photos_not_found,
                modifier = modifier
            )
        },
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .selectableGroup()
                .padding(horizontal = 16.dp)
//                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
        ) {
            StaggeredVerticalGrid(
                maxColumnWidth = 220.dp,
                modifier = Modifier.padding(4.dp)
            ) {
                items.forEach { course ->
                    val selected = true //val selected = course == selectedAnswer
                    RadioButtonLang(
                        modifier = Modifier.padding(vertical = 8.dp),
                        date = course.date.toString(),
                        photo = course.photo,
                        selected = selected,
                        onOptionSelected = { }//onOptionSelected(course) }
                    )
                }
            }
        }
        /** items.forEach { item ->
        HomeTopItem(
        topAsset = item,
        onAssetClick = { onAssetClick(item) }
        )
        }*/
    }
}


*/






@Composable
private fun Header(gender: String) {
    val maleColor = listOf( Color.Blue.copy(.3f), Color.Blue.copy(1f))
    val femaleColor = listOf(Color.Red.copy(0.2f), Color.Red.copy(1f))

    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(if (gender == MALE) maleColor else femaleColor))
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Color(0xff121212).copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = arrowBackIcon(),
            tint = Color(0xffffffff),
            contentDescription = null
        )
    }
}

@Composable
private fun Body(
    user: User?,
    photos: List<UserPhotos>,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            QChatSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(AppText.description),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = user?.let{ it.description } ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0x99000000),
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    BasicDivider()
                    Spacer(Modifier.height(40.dp))
                    Text(
                        text = stringResource(AppText.join),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = user?.let { itUser -> itUser.date?.let{ itLast -> timeCustomFormat(itLast.seconds) } } ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0x99000000),
                        modifier = HzPadding
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    BasicDivider()

                    user?.let {
                        key(it.hobby) {
                            HobbyCollection(hobbies = it.hobby)
                        }
                    }


                    Spacer(Modifier.height(16.dp))
                    BasicDivider()

                    photos?.let {
                        key(it) {
                            PhotosContent(hobbys = photos)
                        }
                    }


                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(user: User?, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    val timestamp = Timestamp.now()
    val age = calculateBirthday(birthday =user?.let { it.birthday } ?: "", timestamp = timestamp)

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color(0xffffffff))
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text =   age,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xde000000),
            modifier = HzPadding
        )
        Text(
            text = "${user?.let{ it.name }} ${user?.let { it.surname }}",
            style = MaterialTheme.typography.titleSmall,
            fontSize = 20.sp,
            color = Color.Black,//Color(0xbdffffff),
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = user?.let{
                if(it.online){
                    stringResource(id = AppText.online)
                }else {
                    it.lastSeen?.let { itTime ->
                        timeCustomFormat(itTime.seconds)
                    }
                }
            } ?: "",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xffded6fe),
            modifier = HzPadding
        )

        Spacer(Modifier.height(8.dp))
        BasicDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        SurfaceImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2,
            constraints.maxWidth - imageWidth,
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun CartBottomBar(modifier: Modifier = Modifier) {
    QChatSurface(modifier) {
        Column {
            BasicDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(HzPadding)
                    .heightIn(min = BottomBarHeight)
            ) {
                AdsBannerToolbar(ads = ADS_USER_PROFILE_BANNER_ID)
            }
        }
    }
}
