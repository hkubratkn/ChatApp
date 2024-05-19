package com.kapirti.video_food_delivery_shopping.ui.presentation.chat.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kapirti.video_food_delivery_shopping.R
import com.kapirti.video_food_delivery_shopping.common.composable.NoSurfaceImage
import com.kapirti.video_food_delivery_shopping.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    user: User,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackPressed: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SmallContactIcon(iconUri = user.photo, size = 32.dp)
                Text(text = "${user.name} ${user.surname}")
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (onBackPressed != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                    )
                }
            }
        },
    )
}

@Composable
fun SmallContactIcon(iconUri: String, size: Dp) {
    NoSurfaceImage(
        imageUrl = iconUri,
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray),
    )
}
