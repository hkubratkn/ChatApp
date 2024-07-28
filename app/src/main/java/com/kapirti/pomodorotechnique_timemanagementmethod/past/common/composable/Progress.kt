package com.kapirti.pomodorotechnique_timemanagementmethod.past.common.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

val primaryDarkColor: Color = Color(0xFF263238)

@Composable
fun LoadingContent(
    //loading: Boolean,
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    //onRefresh: () -> Unit,
    //modifier: Modifier = Modifier,
    //content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
       /** Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }*/
    }
}
