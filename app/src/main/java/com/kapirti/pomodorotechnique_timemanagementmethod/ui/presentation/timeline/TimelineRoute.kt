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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeline

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.EmptyTimeline
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogCancelButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.DialogConfirmButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.PomodoroIcons
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.ReportButton
import java.sql.Time
import kotlin.math.absoluteValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun TimelineRoute(
    openDrawer: () -> Unit,
    isExpandedScreen: Boolean,
    navigateLogin: () -> Unit,
    navigateEdit: () -> Unit,
    // navigateUserProfile: () -> Unit,
    // navigateLike: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimelineViewModel = hiltViewModel(),
) {
    val media by viewModel.timelines.collectAsStateWithLifecycle()
    //val media = viewModel.media
    val player = viewModel.player

    if (media.isEmpty()) {
        EmptyTimeline(modifier)
    } else {
        TimelineVerticalPager(
            modifier,
            media,
            player,
            viewModel::initializePlayer,
            viewModel::releasePlayer,
            viewModel::changePlayerItem,
            openDrawer = openDrawer,
            onReportClick = {
                viewModel.onReportClick(it)
            }
        )
    }

    if (viewModel.showReportDialog.value) {
        AlertDialog(
            title = { Text(stringResource(AppText.report)) },
            text = { Text(stringResource(AppText.report_user)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { viewModel.showReportDialog.value = false } },
            confirmButton = { DialogConfirmButton(AppText.report) { viewModel.onReportButtonClick() } },
            onDismissRequest = { viewModel.showReportDialog.value = false }
        )
    }
    if (viewModel.showReportDone.value) {
        AlertDialog(
            title = { Text(stringResource(AppText.thank_you)) },
            text = { Text(stringResource(AppText.report_done_text)) },
            confirmButton = { DialogConfirmButton(AppText.ok) { viewModel.onReportDoneDismiss() } },
            onDismissRequest = { viewModel.onReportDoneDismiss() }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimelineVerticalPager(
    modifier: Modifier = Modifier,
    mediaItems: List<Timeline>,
    player: Player?,
    onInitializePlayer: () -> Unit = {},
    onReleasePlayer: () -> Unit = {},
    onChangePlayerItem: (uri: Uri?, page: Int) -> Unit = { uri: Uri?, i: Int -> },
    openDrawer: () -> Unit,
    onReportClick: (Timeline) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { mediaItems.count() })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            onChangePlayerItem(Uri.parse(mediaItems[page].uri), pagerState.currentPage)
        }
    }

    val currentOnInitializePlayer by rememberUpdatedState(onInitializePlayer)
    val currentOnReleasePlayer by rememberUpdatedState(onReleasePlayer)
    if (Build.VERSION.SDK_INT > 23) {
        LifecycleStartEffect(true) {
            currentOnInitializePlayer()
            onStopOrDispose {
                currentOnReleasePlayer()
            }
        }
    } else {
        LifecycleResumeEffect(true) {
            currentOnInitializePlayer()
            onPauseOrDispose {
                currentOnReleasePlayer()
            }
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
    ) { page ->
        if (player != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .graphicsLayer {
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                        alpha = lerp(
                            start = 0f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                    },
            ) {
                TimelinePage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    media = mediaItems[page],
                    player = player,
                    page,
                    pagerState,
                )

                MetadataOverlay(modifier = Modifier.padding(16.dp), mediaItem = mediaItems[page])


                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                ) {
                    Column{
                        IconButton(modifier = Modifier.padding(bottom = 8.dp), onClick = openDrawer){
                            Icon(
                                imageVector = PomodoroIcons.Menu,
                                contentDescription = stringResource(AppText.cd_profile_photo),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(24.dp)
                            )
                        }
                        ReportButton(onClick = {onReportClick(mediaItems[page])})
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TimelinePage(
    modifier: Modifier = Modifier,
    media: Timeline,
    player: Player,
    page: Int,
    state: PagerState,
) {
    if (page == state.settledPage) {
        // When in preview, early return a Box with the received modifier preserving layout
        if (LocalInspectionMode.current) {
            Box(modifier = modifier)
            return
        }
        AndroidView(
            factory = { PlayerView(it) },
            update = { playerView ->
                playerView.player = player
            },
            modifier = modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun MetadataOverlay(modifier: Modifier, mediaItem: Timeline) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(999f),
    ) {
        if(true){
//        if (mediaItem.type == TimelineMediaType.VIDEO) {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            val context = LocalContext.current.applicationContext

            // Running on an IO thread for loading metadata from remote urls to reduce lag time
            val duration: State<Long?> = produceState<Long?>(initialValue = null) {
                withContext(Dispatchers.IO) {
                    // Remote url
                    if (mediaItem.uri.contains("https://")) {
                        mediaMetadataRetriever.setDataSource(mediaItem.uri, HashMap<String, String>())
                    } else { // Locally saved files
                        mediaMetadataRetriever.setDataSource(context, Uri.parse(mediaItem.uri))
                    }
                    value =
                        mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                            ?.toLong()
                }
            }
            duration.value?.let {
                val seconds = it / 1000L
                val minutes = seconds / 60L
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "%d:%02d".format(minutes, seconds % 60),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            mediaItem.writerPhoto?.let {
                NoSurfaceImage(
                    imageUrl = it,
                    contentDescription = stringResource(AppText.cd_profile_photo),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                )
            }
            Text(modifier = Modifier.padding(end = 16.dp), text = mediaItem.title)
        }
    }
}
