package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.theme.CardBackgroundBlueColor

@Composable
fun UserItem(
    user: User,
    onClick: (User) -> Unit,
) {
    val backgroundColor = if(user.online) Color.Green.copy(0.4f) else Color.Gray.copy(0.2f)

    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackgroundBlueColor),
        border = BorderStroke(1.dp, backgroundColor),
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {onClick(user)},
        ) {
            NoSurfaceImage(
                imageUrl = user.photo,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .padding(10.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = "${user.name} ${user.surname}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = user.description,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
