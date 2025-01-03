package com.test.test.ui.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.test.model.User
import kotlinx.coroutines.launch


@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onItemClicked: (User) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val users by viewModel.users.collectAsStateWithLifecycle()

    HomeScreen(users, onItemClicked = {
        onItemClicked.invoke(it)
    })
}
