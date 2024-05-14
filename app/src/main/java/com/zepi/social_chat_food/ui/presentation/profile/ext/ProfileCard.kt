package com.zepi.social_chat_food.ui.presentation.profile.ext

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.iraaa.common.composable.NoSurfaceImage

@Composable
fun ProfileCard(
    photo: String,
    name: String,
    surname: String,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        NoSurfaceImage(imageUrl = photo, contentDescription = null,
            Modifier
                .size(50.dp)
                .clip(CircleShape))
        Spacer(Modifier.width(2.dp))
        Text("$name $surname")
    }
}
