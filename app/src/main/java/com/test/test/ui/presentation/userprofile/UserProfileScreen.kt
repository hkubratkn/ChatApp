package com.test.test.ui.presentation.userprofile


import android.text.format.DateUtils
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun UserProfileRoute(
    userId: String,
    modifier: Modifier = Modifier,
    onChatClicked: (String) -> Unit,
    onVideoCallClicked: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LaunchedEffect(userId) {
        viewModel.getUser(userId)
    }

    LaunchedEffect(uiState.navigateRoomId) {
        uiState.navigateRoomId?.let {
            onChatClicked(it)
            viewModel.clearRoomId()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column {
            uiState.user?.let { usr ->
                Text("user id : $userId")
                Text("name : ${usr.name}")
                Text("surname : ${usr.surname}")
                Text("Last seen : ${DateUtils.getRelativeTimeSpanString(usr.lastSeen!!.seconds * 1000) }")
                Button(
                    onClick = {
                        viewModel.generatedRoomId()
                        //viewModel.generatedRoomId()?.let { onChatClicked(it) }
                    }
                ) {
                    Text("chat")
                }
                Button(onClick = onVideoCallClicked) { Text("video call") }
                Button(onClick = {}) { Text("voice call") }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfileRoute(
        userId = "abc",
        onChatClicked = {},
        onVideoCallClicked = {}
    )
}
