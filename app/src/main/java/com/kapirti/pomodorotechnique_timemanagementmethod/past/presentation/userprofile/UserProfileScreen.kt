/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.userprofile
/**
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import kotlin.math.max
import kotlin.math.min


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicDivider
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.QChatSurface
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.SurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.arrowBackIcon
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.calculateBirthday
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.timeCustomFormat
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_USER_PROFILE_BANNER_ID
import com.kapirti.pomodorotechnique_timemanagementmethod.past.core.constants.ConsGender.MALE
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.toReadableString
import com.kapirti.pomodorotechnique_timemanagementmethod.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.chats.ChatsViewModel

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
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    viewModel: ChatsViewModel = hiltViewModel(),
) {
    val chats by viewModel.chats.collectAsStateWithLifecycle()

    UserProfileScreen(
        popUpScreen = popUpScreen,
        onChatNopeClick = onChatNopeClick,
        onChatExistClick = onChatExistClick,
        onLoginClick = onLoginClick,
        navigatePhotos = navigatePhotos,
        navigateVideoCall = navigateVideoCall,
        showInterstitialAds = showInterstitialAds,
        includeUserIdViewModel = includeUserIdViewModel,
        chats = chats,
    )
}

@Composable
private fun UserProfileScreen(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    chats: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat>,
    modifier: Modifier = Modifier,
) {
    UserProfileContent(
        popUpScreen = popUpScreen,
        onChatNopeClick = onChatNopeClick,
        onChatExistClick = onChatExistClick,
        onLoginClick = onLoginClick,
        navigatePhotos = navigatePhotos,
        navigateVideoCall = navigateVideoCall,
        showInterstitialAds = showInterstitialAds,
        includeUserIdViewModel = includeUserIdViewModel,
        chats = chats,
        modifier = modifier,
    )
}

@Composable
private fun UserProfileContent(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    showInterstitialAds: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    chats: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat>,
    modifier: Modifier = Modifier,
) {
    UserProfileBody(
        popUpScreen = popUpScreen,
        onChatNopeClick = onChatNopeClick,
        onChatExistClick = onChatExistClick,
        onLoginClick = onLoginClick,
        navigatePhotos = navigatePhotos,
        navigateVideoCall = navigateVideoCall,
        showInterstialAd = showInterstitialAds,
        chats = chats,
        includeUserIdViewModel = includeUserIdViewModel,
        modifier = modifier
    )
}


@Composable
private fun UserProfileBody(
    popUpScreen: () -> Unit,
    onChatExistClick: () -> Unit,
    onChatNopeClick: () -> Unit,
    onLoginClick: () -> Unit,
    navigatePhotos: () -> Unit,
    navigateVideoCall: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    showInterstialAd: () -> Unit,
    chats: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.Chat>,
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel(),
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            userUid = includeUserIdViewModel.partnerId ?: "",
        )
    }

    val user = viewModel.user.collectAsStateWithLifecycle()
    val userPhotos = viewModel.userPhotos.collectAsStateWithLifecycle()

    val missList = arrayListOf(viewModel.uid)
    for (mek in chats) {
        missList.add(mek.partnerUid.toString())
    }

    Scaffold(
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
                Body(
                    user = user.value,
                    photos = userPhotos.value,
                    scroll = scroll,
                    onLongClick = navigatePhotos,
                    onChatClick = {
                        showInterstialAd()
                        if (viewModel.hasUser) {
                            val result = user.value.uid in missList

                            if (result) {
                                onChatExistClick()
                            } else {
                                onChatNopeClick()
                            }
                        } else {
                            onLoginClick()
                        }
                    },
                    onVideoClick = navigateVideoCall,
                    onSoundClick = navigateVideoCall,
                )
                Title(user.value) { scroll.value }
                Image(user.value.photo) { scroll.value }
                Up(popUpScreen)
                CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}

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
    user: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?,
    photos: List<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserPhotos>,
    scroll: ScrollState,
    onLongClick: () -> Unit,
    onChatClick: () -> Unit,
    onSoundClick: () -> Unit,
    onVideoClick: () -> Unit,
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
                        text = user?.let { itUser -> itUser.date?.let{ itLast -> itLast.seconds.toReadableString() } } ?: "",
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

                    if(photos.isNotEmpty()){
                        key(photos) {
                            PhotosContent(
                                photos = photos,
                                onLongClick = onLongClick
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    BasicDivider()

                    CallRow(
                        onChatClick = onChatClick,
                        onSoundClick = onSoundClick,
                        onVideoClick = onVideoClick,
                    )


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
private fun Title(user: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.User?, scrollProvider: () -> Int) {
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

@Composable
private fun CallRow(
    onChatClick: () -> Unit,
    onSoundClick: () -> Unit,
    onVideoClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onChatClick) { Icon(Icons.Default.ChatBubble, contentDescription = null) }
        IconButton(onClick = onSoundClick) { Icon(Icons.Default.Mic, contentDescription = null) }
        IconButton(onClick = onVideoClick) { Icon(Icons.Default.VideoCall, contentDescription = null) }
    }
}
*/
