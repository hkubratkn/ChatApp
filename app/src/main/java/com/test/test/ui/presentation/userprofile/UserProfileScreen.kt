package com.test.test.ui.presentation.userprofile


import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview


@Composable
internal fun UserProfileRoute(
    userId: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Column {
            Text("user id : $userId")
            Text("lastseen")
            Button(onClick = {}) { Text("chat") }
            Button(onClick = {}) { Text("video call") }
            Button(onClick = {}) { Text("voice call") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    UserProfileRoute(
        userId = "abc"
    )
}
