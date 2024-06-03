package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Timeline(
    modifier: Modifier = Modifier,
//    viewModel: TimelineViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Timeline Screen")
    }

}
 /**   val media = viewModel.media
    val player = viewModel.player
    val videoRatio = viewModel.videoRatio

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
            videoRatio,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimelineVerticalPager(
    modifier: Modifier = Modifier,
    mediaItems: List<TimelineMediaItem>,
    player: Player?,
    onInitializePlayer: () -> Unit = {},
    onReleasePlayer: () -> Unit = {},
    onChangePlayerItem: (uri: Uri?) -> Unit = {},
    videoRatio: Float?,
) {
    val pagerState = rememberPagerState(pageCount = { mediaItems.count() })
    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the settledPage
        snapshotFlow { pagerState.settledPage }.collect { page ->
            if (mediaItems[page].type == TimelineMediaType.VIDEO) {
                onChangePlayerItem(Uri.parse(mediaItems[page].uri))
            } else {
                onChangePlayerItem(null)
            }
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
        modifier = modifier
            .fillMaxSize(),
    ) { page ->
        if (player != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                        // We animate the alpha, between 0% and 100%
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
                    videoRatio,
                )

                MetadataOverlay(modifier = Modifier.padding(16.dp), mediaItem = mediaItems[page])
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimelinePage(
    modifier: Modifier = Modifier,
    media: TimelineMediaItem,
    player: Player,
    page: Int,
    state: PagerState,
    videoRatio: Float?,
) {
    when (media.type) {
        TimelineMediaType.VIDEO -> {
            if (page == state.settledPage) {
                // Use a default 1:1 ratio if the video size is unknown
                val sanitizedRatio = videoRatio ?: 1f
                AndroidExternalSurface(
                    modifier = modifier
                        .aspectRatio(sanitizedRatio, sanitizedRatio < 1f)
                        .background(Color.White),
                ) {
                    onSurface { surface, _, _ ->
                        player.setVideoSurface(surface)

                        // Cleanup when surface is destroyed
                        surface.onDestroyed {
                            player.clearVideoSurface(this)
                            release()
                        }
                    }
                }
            }
        }
        TimelineMediaType.PHOTO -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(media.uri)
                    .build(),
                contentDescription = null,
                modifier = modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
fun MetadataOverlay(modifier: Modifier, mediaItem: TimelineMediaItem) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(999f),
    ) {
        if (mediaItem.type == TimelineMediaType.VIDEO) {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(
                LocalContext.current,
                Uri.parse(mediaItem.uri),
            )

            val duration =
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    ?.toLong()
            duration?.let {
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
            mediaItem.chatIconUri?.let {
                Image(
                    painter = rememberIconPainter(contentUri = it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                )
            }
            Text(modifier = Modifier.padding(end = 16.dp), text = mediaItem.chatName)
        }
    }
}

@Composable
fun EmptyTimeline(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(64.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_timeline),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.timeline_empty_title),
            modifier = Modifier.padding(top = 64.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.timeline_empty_message),
            textAlign = TextAlign.Center,
        )
    }
}
*/
