package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.chat.ext


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.model.ChatMessage
import com.kapirti.pomodorotechnique_timemanagementmethod.model.User


@Composable
fun MessageList(
    me: User,
    partner: User,
    messages: List<ChatMessage>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
   // onVideoClick: (uri: String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
    ) {
        items(items = messages) { message ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    if(message.senderId == partner.uid) Alignment.Start else Alignment.End,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val iconSize = 48.dp
                SmallContactIcon(
                    iconUri = if (message.senderId == partner.uid){
                        partner.photo
                    } else {
                        me.photo
                    }, size = iconSize)

                MessageBubble(
                    isIncoming = if (message.senderId == partner.uid) true else false,
                    message = message,
                    //onVideoClick = {}, //{ message.mediaUri?.let { onVideoClick(it) } },
                )
            }
        }
    }
}

@Composable
private fun MessageBubble(
    isIncoming: Boolean,
    message: ChatMessage,
    modifier: Modifier = Modifier,
   // onVideoClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier,
        color = if (isIncoming) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.primary
        },
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = message.text)
/**            if (message.mediaUri != null) {
                val mimeType = message.mediaMimeType
                if (mimeType != null) {
                    if (mimeType.contains("image")) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(message.mediaUri)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(250.dp)
                                .padding(10.dp),
                        )
                    } else if (mimeType.contains("video")) {
                        VideoMessagePreview(
                            videoUri = message.mediaUri,
                            onClick = onVideoClick,
                        )
                    } else {
                        Log.e(TAG, "Unrecognized media type")
                    }
                } else {
                    Log.e(TAG, "No MIME type associated with media object")
                }
            }*/
        }
    }
}




/**


private const val TAG = "ChatUI"






@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val bitmapState = produceState<Bitmap?>(initialValue = null) {
        withContext(Dispatchers.IO) {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
// Return any frame that the framework considers representative of a valid frame
            value = mediaMetadataRetriever.frameAtTime
        }
    }

    bitmapState.value?.let { bitmap ->
        Box(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(10.dp),
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Darken),
            )

            Icon(
                Icons.Filled.PlayArrow,
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
                    .border(3.dp, Color.White, shape = CircleShape),
            )
        }
    }
}*/







/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.ira.model.ChatMessage


private const val TAG = "ChatUI"

@Composable
fun MessageList(
messages: List<ChatMessage>,
contentPadding: PaddingValues,
modifier: Modifier = Modifier,
onVideoClick: (uri: String) -> Unit = {},
) {
LazyColumn(
modifier = modifier,
contentPadding = contentPadding,
reverseLayout = true,
verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
) {
items(items = messages) { message ->
Row(
modifier = Modifier
.fillMaxWidth()
.padding(horizontal = 16.dp),
horizontalArrangement = Arrangement.spacedBy(
16.dp,
if (message.isIncoming) Alignment.Start else Alignment.End,
),
verticalAlignment = Alignment.CenterVertically,
) {
val iconSize = 48.dp
if (message.senderIcon != null) {
SmallContactIcon(iconUri = message.senderIcon, size = iconSize)
} else {
Spacer(modifier = Modifier.size(iconSize))
}
MessageBubble(
message = message,
onVideoClick = { message.mediaUri?.let { onVideoClick(it) } },
)
}
}
}
}


@Composable
private fun MessageBubble(
message: ChatMessage,
modifier: Modifier = Modifier,
onVideoClick: () -> Unit = {},
) {
Surface(
modifier = modifier,
color = if (message.isIncoming) {
MaterialTheme.colorScheme.secondaryContainer
} else {
MaterialTheme.colorScheme.primary
},
shape = MaterialTheme.shapes.large,
) {
Column(
modifier = Modifier.padding(16.dp),
) {
Text(text = message.text)
if (message.mediaUri != null) {
val mimeType = message.mediaMimeType
if (mimeType != null) {
if (mimeType.contains("image")) {
AsyncImage(
model = ImageRequest.Builder(LocalContext.current)
.data(message.mediaUri)
.build(),
contentDescription = null,
modifier = Modifier
.height(250.dp)
.padding(10.dp),
)
} else if (mimeType.contains("video")) {
VideoMessagePreview(
videoUri = message.mediaUri,
onClick = onVideoClick,
)
} else {
Log.e(TAG, "Unrecognized media type")
}
} else {
Log.e(TAG, "No MIME type associated with media object")
}
}
}
}
}



@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
val context = LocalContext.current.applicationContext
val bitmapState = produceState<Bitmap?>(initialValue = null) {
withContext(Dispatchers.IO) {
val mediaMetadataRetriever = MediaMetadataRetriever()
mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
// Return any frame that the framework considers representative of a valid frame
value = mediaMetadataRetriever.frameAtTime
}
}

bitmapState.value?.let { bitmap ->
Box(
modifier = Modifier
.clickable(onClick = onClick)
.padding(10.dp),
) {
Image(
bitmap = bitmap.asImageBitmap(),
contentDescription = null,
colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Darken),
)

Icon(
Icons.Filled.PlayArrow,
tint = Color.White,
contentDescription = null,
modifier = Modifier
.size(50.dp)
.align(Alignment.Center)
.border(3.dp, Color.White, shape = CircleShape),
)
}
}
}
 */
/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.ira.model.ChatMessage


private const val TAG = "ChatUI"

@Composable
fun MessageList(
messages: List<ChatMessage>,
contentPadding: PaddingValues,
modifier: Modifier = Modifier,
onVideoClick: (uri: String) -> Unit = {},
) {
LazyColumn(
modifier = modifier,
contentPadding = contentPadding,
reverseLayout = true,
verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
) {
items(items = messages) { message ->
Row(
modifier = Modifier
.fillMaxWidth()
.padding(horizontal = 16.dp),
horizontalArrangement = Arrangement.spacedBy(
16.dp,
if (message.isIncoming) Alignment.Start else Alignment.End,
),
verticalAlignment = Alignment.CenterVertically,
) {
val iconSize = 48.dp
if (message.senderIcon != null) {
SmallContactIcon(iconUri = message.senderIcon, size = iconSize)
} else {
Spacer(modifier = Modifier.size(iconSize))
}
MessageBubble(
message = message,
onVideoClick = { message.mediaUri?.let { onVideoClick(it) } },
)
}
}
}
}


@Composable
private fun MessageBubble(
message: ChatMessage,
modifier: Modifier = Modifier,
onVideoClick: () -> Unit = {},
) {
Surface(
modifier = modifier,
color = if (message.isIncoming) {
MaterialTheme.colorScheme.secondaryContainer
} else {
MaterialTheme.colorScheme.primary
},
shape = MaterialTheme.shapes.large,
) {
Column(
modifier = Modifier.padding(16.dp),
) {
Text(text = message.text)
if (message.mediaUri != null) {
val mimeType = message.mediaMimeType
if (mimeType != null) {
if (mimeType.contains("image")) {
AsyncImage(
model = ImageRequest.Builder(LocalContext.current)
.data(message.mediaUri)
.build(),
contentDescription = null,
modifier = Modifier
.height(250.dp)
.padding(10.dp),
)
} else if (mimeType.contains("video")) {
VideoMessagePreview(
videoUri = message.mediaUri,
onClick = onVideoClick,
)
} else {
Log.e(TAG, "Unrecognized media type")
}
} else {
Log.e(TAG, "No MIME type associated with media object")
}
}
}
}
}



@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
val context = LocalContext.current.applicationContext
val bitmapState = produceState<Bitmap?>(initialValue = null) {
withContext(Dispatchers.IO) {
val mediaMetadataRetriever = MediaMetadataRetriever()
mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
// Return any frame that the framework considers representative of a valid frame
value = mediaMetadataRetriever.frameAtTime
}
}

bitmapState.value?.let { bitmap ->
Box(
modifier = Modifier
.clickable(onClick = onClick)
.padding(10.dp),
) {
Image(
bitmap = bitmap.asImageBitmap(),
contentDescription = null,
colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Darken),
)

Icon(
Icons.Filled.PlayArrow,
tint = Color.White,
contentDescription = null,
modifier = Modifier
.size(50.dp)
.align(Alignment.Center)
.border(3.dp, Color.White, shape = CircleShape),
)
}
}
}
 */
/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.ira.model.ChatMessage


private const val TAG = "ChatUI"

@Composable
fun MessageList(
messages: List<ChatMessage>,
contentPadding: PaddingValues,
modifier: Modifier = Modifier,
onVideoClick: (uri: String) -> Unit = {},
) {
LazyColumn(
modifier = modifier,
contentPadding = contentPadding,
reverseLayout = true,
verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
) {
items(items = messages) { message ->
Row(
modifier = Modifier
.fillMaxWidth()
.padding(horizontal = 16.dp),
horizontalArrangement = Arrangement.spacedBy(
16.dp,
if (message.isIncoming) Alignment.Start else Alignment.End,
),
verticalAlignment = Alignment.CenterVertically,
) {
val iconSize = 48.dp
if (message.senderIcon != null) {
SmallContactIcon(iconUri = message.senderIcon, size = iconSize)
} else {
Spacer(modifier = Modifier.size(iconSize))
}
MessageBubble(
message = message,
onVideoClick = { message.mediaUri?.let { onVideoClick(it) } },
)
}
}
}
}


@Composable
private fun MessageBubble(
message: ChatMessage,
modifier: Modifier = Modifier,
onVideoClick: () -> Unit = {},
) {
Surface(
modifier = modifier,
color = if (message.isIncoming) {
MaterialTheme.colorScheme.secondaryContainer
} else {
MaterialTheme.colorScheme.primary
},
shape = MaterialTheme.shapes.large,
) {
Column(
modifier = Modifier.padding(16.dp),
) {
Text(text = message.text)
if (message.mediaUri != null) {
val mimeType = message.mediaMimeType
if (mimeType != null) {
if (mimeType.contains("image")) {
AsyncImage(
model = ImageRequest.Builder(LocalContext.current)
.data(message.mediaUri)
.build(),
contentDescription = null,
modifier = Modifier
.height(250.dp)
.padding(10.dp),
)
} else if (mimeType.contains("video")) {
VideoMessagePreview(
videoUri = message.mediaUri,
onClick = onVideoClick,
)
} else {
Log.e(TAG, "Unrecognized media type")
}
} else {
Log.e(TAG, "No MIME type associated with media object")
}
}
}
}
}



@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
val context = LocalContext.current.applicationContext
val bitmapState = produceState<Bitmap?>(initialValue = null) {
withContext(Dispatchers.IO) {
val mediaMetadataRetriever = MediaMetadataRetriever()
mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
// Return any frame that the framework considers representative of a valid frame
value = mediaMetadataRetriever.frameAtTime
}
}

bitmapState.value?.let { bitmap ->
Box(
modifier = Modifier
.clickable(onClick = onClick)
.padding(10.dp),
) {
Image(
bitmap = bitmap.asImageBitmap(),
contentDescription = null,
colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Darken),
)

Icon(
Icons.Filled.PlayArrow,
tint = Color.White,
contentDescription = null,
modifier = Modifier
.size(50.dp)
.align(Alignment.Center)
.border(3.dp, Color.White, shape = CircleShape),
)
}
}
}
 */
/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.ira.model.ChatMessage


private const val TAG = "ChatUI"

@Composable
fun MessageList(
messages: List<ChatMessage>,
contentPadding: PaddingValues,
modifier: Modifier = Modifier,
onVideoClick: (uri: String) -> Unit = {},
) {
LazyColumn(
modifier = modifier,
contentPadding = contentPadding,
reverseLayout = true,
verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
) {
items(items = messages) { message ->
Row(
modifier = Modifier
.fillMaxWidth()
.padding(horizontal = 16.dp),
horizontalArrangement = Arrangement.spacedBy(
16.dp,
if (message.isIncoming) Alignment.Start else Alignment.End,
),
verticalAlignment = Alignment.CenterVertically,
) {
val iconSize = 48.dp
if (message.senderIcon != null) {
SmallContactIcon(iconUri = message.senderIcon, size = iconSize)
} else {
Spacer(modifier = Modifier.size(iconSize))
}
MessageBubble(
message = message,
onVideoClick = { message.mediaUri?.let { onVideoClick(it) } },
)
}
}
}
}


@Composable
private fun MessageBubble(
message: ChatMessage,
modifier: Modifier = Modifier,
onVideoClick: () -> Unit = {},
) {
Surface(
modifier = modifier,
color = if (message.isIncoming) {
MaterialTheme.colorScheme.secondaryContainer
} else {
MaterialTheme.colorScheme.primary
},
shape = MaterialTheme.shapes.large,
) {
Column(
modifier = Modifier.padding(16.dp),
) {
Text(text = message.text)
if (message.mediaUri != null) {
val mimeType = message.mediaMimeType
if (mimeType != null) {
if (mimeType.contains("image")) {
AsyncImage(
model = ImageRequest.Builder(LocalContext.current)
.data(message.mediaUri)
.build(),
contentDescription = null,
modifier = Modifier
.height(250.dp)
.padding(10.dp),
)
} else if (mimeType.contains("video")) {
VideoMessagePreview(
videoUri = message.mediaUri,
onClick = onVideoClick,
)
} else {
Log.e(TAG, "Unrecognized media type")
}
} else {
Log.e(TAG, "No MIME type associated with media object")
}
}
}
}
}



@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
val context = LocalContext.current.applicationContext
val bitmapState = produceState<Bitmap?>(initialValue = null) {
withContext(Dispatchers.IO) {
val mediaMetadataRetriever = MediaMetadataRetriever()
mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
// Return any frame that the framework considers representative of a valid frame
value = mediaMetadataRetriever.frameAtTime
}
}

bitmapState.value?.let { bitmap ->
Box(
modifier = Modifier
.clickable(onClick = onClick)
.padding(10.dp),
) {
Image(
bitmap = bitmap.asImageBitmap(),
contentDescription = null,
colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Darken),
)

Icon(
Icons.Filled.PlayArrow,
tint = Color.White,
contentDescription = null,
modifier = Modifier
.size(50.dp)
.align(Alignment.Center)
.border(3.dp, Color.White, shape = CircleShape),
)
}
}
}
 */
