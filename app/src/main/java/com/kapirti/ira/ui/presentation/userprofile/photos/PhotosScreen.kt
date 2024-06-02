package com.kapirti.ira.ui.presentation.userprofile.photos

import androidx.compose.material.icons.Icons
import com.kapirti.ira.R.string as AppText
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kapirti.ira.common.EmptyContent
import com.kapirti.ira.common.composable.NoSurfaceImage
import com.kapirti.ira.common.composable.StaggeredVerticalGrid
import com.kapirti.ira.core.constants.ConsGender
import com.kapirti.ira.model.UserPhotos

@Composable
fun PhotosScreen(
    userPhotos: List<UserPhotos>,
    modifier: Modifier = Modifier,
) {
    if (userPhotos.isEmpty()){
        EmptyContent(icon = Icons.Default.PhotoAlbum, label = AppText.no_photos_all)
    } else {
        PhotoBody(
            userPhotos = userPhotos,
            modifier = modifier,
        )
    }
}



@Composable
private fun PhotoBody(
    userPhotos: List<UserPhotos>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .selectableGroup()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            userPhotos.forEach { userPhoto ->
                PhotoSingle(
                    imageId = userPhoto.photo,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun PhotoSingle(
    imageId: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            NoSurfaceImage(
                imageUrl = imageId,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
        }
    }
}
