package com.zepi.social_chat_food.soci.ui.photopicker.navigation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.zepi.social_chat_food.soci.ui.photopicker.PhotoPickerViewModel

@Composable
fun PhotoPickerRoute(
    viewModel: PhotoPickerViewModel = hiltViewModel(),
    onPhotoPicked: () -> Unit,
) {
    val photoPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri: Uri? ->
                if (uri != null) {
                    viewModel.onPhotoPicked(uri)
                }
                onPhotoPicked()
            },
        )
    LaunchedEffect(Unit) {
        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo),
        )
    }
}
