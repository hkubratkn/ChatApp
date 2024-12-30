package com.test.test.ui.presentation.userprofile


import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
internal fun UserProfileRoute(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Text("lastseen")
        Button(onClick = {}) { Text("chat") }
        Button(onClick = {}) { Text("video call") }
        Button(onClick = {}) { Text("voice call") }
    }
}
