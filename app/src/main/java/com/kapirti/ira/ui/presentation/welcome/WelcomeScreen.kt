@file:OptIn(
    ExperimentalFoundationApi::class
)
@file:Suppress("unused")

package com.kapirti.ira.ui.presentation.welcome

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
//import com.example.compose.snippets.util.rememberRandomSampleImageUrl
import kotlin.math.absoluteValue
import kotlinx.coroutines.launch


@Preview
@Composable
fun HorizontalPagerSample() {
    // [START android_compose_layouts_pager_horizontal_basic]
    // Display 10 items
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    HorizontalPager(state = pagerState) { page ->
        // Our page content
        Text(
            text = "Page: $page",
            modifier = Modifier.fillMaxWidth()
        )
    }
    // [END android_compose_layouts_pager_horizontal_basic]
}

@Preview
@Composable
fun VerticalPagerSample() {
    // [START android_compose_layouts_pager_vertical_basic]
    // Display 10 items
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    VerticalPager(state = pagerState) { page ->
        // Our page content
        Text(
            text = "Page: $page",
            modifier = Modifier.fillMaxWidth()
        )
    }
    // [END android_compose_layouts_pager_vertical_basic]
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PagerScrollToItem() {
    Box {
        // [START android_compose_layouts_pager_scroll]
        val pagerState = rememberPagerState(pageCount = {
            10
        })
        HorizontalPager(state = pagerState) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }

        // scroll to page
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                // Call scroll to on pagerState
                pagerState.scrollToPage(5)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("Jump to Page 5")
        }
        // [END android_compose_layouts_pager_scroll]
    }
}

@Preview
@Composable
fun PagerAnimateToItem() {
    Box {
        // [START android_compose_layouts_pager_scroll_animate]
        val pagerState = rememberPagerState(pageCount = {
            10
        })

        HorizontalPager(state = pagerState) { page ->
            // Our page content
            Text(
                text = "Page: $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }

        // scroll to page
        val coroutineScope = rememberCoroutineScope()
        Button(onClick = {
            coroutineScope.launch {
                // Call scroll to on pagerState
                pagerState.animateScrollToPage(5)
            }
        }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text("Jump to Page 5")
        }
        // [END android_compose_layouts_pager_scroll_animate]
    }
}

@Preview
@Composable
fun PageChangesSample() {
    // [START android_compose_layouts_pager_notify_page_changes]
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Log.d("Page change", "Page changed to $page")
        }
    }

    VerticalPager(
        state = pagerState,
    ) { page ->
        Text(text = "Page: $page")
    }
    // [END android_compose_layouts_pager_notify_page_changes]
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun PagerWithTabsExample() {
    val pages = listOf("Movies", "Books", "Shows", "Fun")
    // [START android_compose_layouts_pager_tabs]
    val pagerState = rememberPagerState(pageCount = {
        pages.size
    })

    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
    ) {
        // Add tabs for all of our pages
        pages.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = { },
            )
        }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
        Text("Page: ${pages[page]}")
    }
    // [END android_compose_layouts_pager_tabs]
}

@Preview
@Composable
fun PagerWithEffect() {
    // [START android_compose_layouts_pager_transformation]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(state = pagerState) { page ->
        Card(
            Modifier
                .size(200.dp)
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            // Card content
        }
    }
    // [END android_compose_layouts_pager_transformation]
}

@Composable
@Preview
fun PagerStartPadding() {
    // [START android_compose_layouts_pager_padding_start]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(start = 64.dp),
    ) { page ->
        // page content
    }
    // [END android_compose_layouts_pager_padding_start]
}

@Preview
@Composable
fun PagerHorizontalPadding() {
    // [START android_compose_layouts_pager_padding_horizontal]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
    ) { page ->
        // page content
    }
    // [END android_compose_layouts_pager_padding_horizontal]
}

@Preview
@Composable
fun PagerEndPadding() {
    // [START android_compose_layouts_pager_padding_end]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 64.dp),
    ) { page ->
        // page content
    }
    // [END android_compose_layouts_pager_padding_end]
}

@Preview
@Composable
fun PagerCustomSizes() {
    // [START android_compose_layouts_pager_custom_size]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(100.dp)
    ) { page ->
        // page content
    }
    // [END android_compose_layouts_pager_custom_size]
}

@Preview
@Composable
fun PagerWithTabs() {
    // [START android_compose_layouts_pager_with_tabs]
    val pagerState = rememberPagerState(pageCount = {
        4
    })
    HorizontalPager(
        state = pagerState,
    ) { page ->
        // page content
    }
    // [END android_compose_layouts_pager_with_tabs]
}
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PagerIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        // [START android_compose_pager_indicator]
        val pagerState = rememberPagerState(pageCount = {
            4
        })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            // Our page content
            Text(
                text = "Page: $page",
            )
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
        // [END android_compose_pager_indicator]
    }
}

// [START android_compose_pager_custom_page_size]
private val threePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2 * pageSpacing) / 3
    }
}
// [END android_compose_pager_custom_page_size]

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun CustomSnapDistance() {
    // [START android_compose_pager_custom_snap_distance]
    val pagerState = rememberPagerState(pageCount = { 10 })

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(200.dp),
            beyondViewportPageCount = 10,
//            beyondBoundsPageCount = 10,
            flingBehavior = fling
        ) {
            PagerSampleItem(page = it)
        }
    }
    // [END android_compose_pager_custom_snap_distance]
}

@Composable
internal fun PagerSampleItem(
    page: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        // Our page content, displaying a random image
        Icon(
            imageVector = Icons.Default.Star,
            //painter = rememberAsyncImagePainter(model = rememberRandomSampleImageUrl(width = 600)),
            //contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        // Displays the page index
        Text(
            text = page.toString(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(4.dp))
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}


/**
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.ira.common.composable.AdsBannerToolbar
import com.kapirti.ira.R.string as AppText
import com.kapirti.ira.R.drawable as AppIcon
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.kapirti.ira.core.constants.ConsAds.ADS_WELCOME_BANNER_ID

@ExperimentalAnimationApi
@Composable
fun WelcomeScreen(
    openAndPopUp: (String, String) -> Unit,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState(3)

    Scaffold(topBar = { AdsBannerToolbar(ADS_WELCOME_BANNER_ID) }) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding).fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
               // page = 3,
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
/**            HorizontalPagerIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally).weight(1f),
                pagerState = pagerState
            )*/
            FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                viewModel.saveOnBoardingState(completed = true, openAndPopUp = openAndPopUp)
                showInterstialAd()
            }
        }
    }
}

@Composable
private fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = null
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = onBoardingPage.title),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp).padding(top = 20.dp),
            text = stringResource(id = onBoardingPage.description),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(id = AppText.finish))
            }
        }
    }
}

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    object First : OnBoardingPage(
        image =AppIcon.unnamed,
        title = AppText.app_name,
        description = AppText.app_name
    )

    object Second : OnBoardingPage(
        image =AppIcon.unnamed,
        title = AppText.app_name,
        description = AppText.app_name
    )

    object Third : OnBoardingPage(
        image =AppIcon.unnamed,
        title = AppText.app_name,
        description = AppText.app_name
    )
}
*/
