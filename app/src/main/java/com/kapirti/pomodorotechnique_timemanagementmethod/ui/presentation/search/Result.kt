package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
//import androidx.constraintlayout.compose.ChainStyle
//import androidx.constraintlayout.compose.ConstraintLayout
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.R.drawable as AppIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.NoSurfaceImage
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.toReadableString

@Composable
fun SearchResults(
    searchResults: List<User>,
    onUserClick: (User) -> Unit
) {
    Column {
        Text(
            text = stringResource(AppText.search_count, searchResults.size),
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xffded6fe),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
        LazyColumn {
            itemsIndexed(searchResults) { index, snack ->
                SearchResult(snack, onClick = onUserClick)
            }
        }
    }
}

@Composable
private fun SearchResult(
    user: User,
    onClick: ((User) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable(onClick = {onClick(user)}) else Modifier,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        NoSurfaceImage(imageUrl = user.photo,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "${user.name} ${user.surname}",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
            )
            Text(
                text = user.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = user.date?.seconds?.toReadableString() ?: "",
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(AppIcon.ic_send),
            null
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(AppText.search_no_matches, query),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(AppText.search_no_matches_retry),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
