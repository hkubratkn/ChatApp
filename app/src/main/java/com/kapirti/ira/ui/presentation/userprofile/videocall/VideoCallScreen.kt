package com.kapirti.ira.ui.presentation.userprofile.videocall

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.core.call.state.FlipCamera
import io.getstream.video.android.core.call.state.LeaveCall
import io.getstream.video.android.core.call.state.ToggleCamera
import io.getstream.video.android.core.call.state.ToggleMicrophone
import io.getstream.video.android.model.User
import kotlinx.coroutines.launch

@Composable
fun VideoCallScreen(
    popUp: () -> Unit,
    viewModel: VideoCallViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val userId = "Talon_Karrde"
    val callId = "Rtm5ypJ3HVR3"
    val userToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiVGFsb25fS2FycmRlIiwiaXNzIjoiaHR0cHM6Ly9wcm9udG8uZ2V0c3RyZWFtLmlvIiwic3ViIjoidXNlci9UYWxvbl9LYXJyZGUiLCJpYXQiOjE3MTcyMzM0NTgsImV4cCI6MTcxNzgzODI2M30.CySTvRRvqLNMq2IYKTSezr3aArlqvn2dnFGovzE4Bac"
    //StreamVideo.devToken(userId)

    // step1 - create a user.
    val user = User(
        id = userId, // any string
        name = "Tutorial", // name and image are used in the UI
    )

    // step2 - initialize StreamVideo. For a production app we recommend adding
    // the client to your Application class or di module.
    val client = StreamVideoBuilder(
        context = context,
        apiKey = "mmhfdzb5evj2",
        geo = GEO.GlobalEdgeNetwork,
        user = user,
        token = userToken,
    ).build()

    // step3 - create a call.
    val call = client.call("default", callId)


    // step4 - join a call.
    LaunchCallPermissions(call) {
        val result = call.join(create = true)
        result.onError {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()

    VideoTheme {
        // step5 - render the pre-built call content.
        CallContent(
            modifier = Modifier.background(color = VideoTheme.colors.baseTertiary),
            call = call,
            onBackPressed = { popUp() },
            onCallAction = { callAction ->
                when (callAction) {
                    is ToggleCamera -> call.camera.setEnabled(callAction.isEnabled)

                    is ToggleMicrophone -> call.microphone.setEnabled(callAction.isEnabled)

                    is FlipCamera -> call.camera.flip()

                    LeaveCall -> {
                        scope.launch {
                            call.leave()
                            popUp()
                        }
                    }

                    else -> Unit
                }
            },
        )
    }
}
