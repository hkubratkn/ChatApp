package com.zepi.social_chat_food.iraaa.ui.presentation.userprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.common.composable.NoSurfaceImage
import com.zepi.social_chat_food.iraaa.common.composable.QChatSurface
import com.zepi.social_chat_food.iraaa.common.ext.getYearFromTimeStamp
import com.zepi.social_chat_food.iraaa.model.UserPhotos


@Composable
fun PhotosContent(
    hobbys: List<UserPhotos>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = stringResource(AppText.selfie_skills),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
        }
        Photos(hobbys)
    }
}

@Composable
private fun Photos(
    photos: List<UserPhotos>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
    ) {
        items(photos) { hobby ->
            PhotoItem(hobby)
        }
    }
}

@Composable
private fun PhotoItem(
    userPhoto: UserPhotos,
    modifier: Modifier = Modifier
) {
    val year = userPhoto.date?.let { getYearFromTimeStamp(it.seconds) }

    QChatSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            NoSurfaceImage(
                imageUrl = userPhoto.photo,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = year ?: "2024",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}