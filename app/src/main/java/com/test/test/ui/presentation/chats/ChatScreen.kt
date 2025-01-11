package com.test.test.ui.presentation.chats

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.test.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ChatScreen(
    chatId: String,
    //firstUserId: String,
    //secondUserId: String,
    //name: String,
    foreground: Boolean = false,
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = {},
    onCameraClick: () -> Unit = {},
    onPhotoPickerClick: () -> Unit = {},
    onVideoClick: (uri: String) -> Unit = {},
    prefilledText: String? = null,
    viewModel: ChatViewModel = hiltViewModel(),
) {

    LaunchedEffect(chatId) {
        viewModel.setChatId(chatId)
        if (prefilledText != null) {
            viewModel.prefillInput(prefilledText)
        }
    }

    //LaunchedEffect(firstUserId, secondUserId) {
    //    viewModel.fetchConversation(firstUserId, secondUserId)
    //}

    val uiState by viewModel.uiState

//    val chat by viewModel.chat.collectAsStateWithLifecycle()
    //val messages = emptyList<ChatMessage>()
    //val messages by viewModel.messages.collectAsStateWithLifecycle()
    val input by viewModel.input.collectAsStateWithLifecycle()
//    val sendEnabled by viewModel.sendEnabled.collectAsStateWithLifecycle()

    uiState.chatRoom?.let { chatRoom ->
        ChatContent(
            //chat = c,
            name = "DENEME",
            messages = uiState.messages,
            input = input,
            sendEnabled = true,
            onBackPressed = onBackPressed,
            onInputChanged = { viewModel.updateInput(it) },
            onSendClick = { viewModel.send() },
            onCameraClick = onCameraClick,
            onPhotoPickerClick = onPhotoPickerClick,
            onVideoClick = onVideoClick,
            modifier = modifier
                .clip(RoundedCornerShape(5)),
        )
    }
//    LifecycleEffect(
//        onResume = { viewModel.setForeground(foreground) },
//        onPause = { viewModel.setForeground(false) },
//    )
}

@Composable
private fun LifecycleEffect(
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val listener = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                onResume()
            }

            override fun onPause(owner: LifecycleOwner) {
                onPause()
            }
        }
        lifecycle.addObserver(listener)
        onDispose {
            lifecycle.removeObserver(listener)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatContent(
    //chat: ChatDetail,
    name: String,
    messages: List<ChatMessage>,
    input: String,
    sendEnabled: Boolean,
    onBackPressed: (() -> Unit)?,
    onInputChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onCameraClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    onVideoClick: (uri: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ChatAppBar(
                //chat = chat,
                name = name,
                scrollBehavior = scrollBehavior,
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column {
            val layoutDirection = LocalLayoutDirection.current
            MessageList(
                messages = messages,
                contentPadding = innerPadding.copy(layoutDirection, bottom = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onVideoClick = onVideoClick,
            )
            InputBar(
                input = input,
                onInputChanged = onInputChanged,
                onSendClick = onSendClick,
                onCameraClick = onCameraClick,
                onPhotoPickerClick = onPhotoPickerClick,
                contentPadding = innerPadding.copy(layoutDirection, top = 0.dp),
                sendEnabled = sendEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime.exclude(WindowInsets.navigationBars)),
            )
        }
    }
}

private fun PaddingValues.copy(
    layoutDirection: LayoutDirection,
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
) = PaddingValues(
    start = start ?: calculateStartPadding(layoutDirection),
    top = top ?: calculateTopPadding(),
    end = end ?: calculateEndPadding(layoutDirection),
    bottom = bottom ?: calculateBottomPadding(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatAppBar(
    //chat: ChatDetail,
    name: String,
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
                // This only supports DM for now.
                val contact = "deneme"//chat.attendees.first()
                //SmallContactIcon(iconUri = contact.iconUri, size = 32.dp)
                //Text(text = contact.name)
                Text(text = name)
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (onBackPressed != null) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(android.R.string.cancel),
                    )
                }
            }
        },
    )
}

@Composable
private fun SmallContactIcon(iconUri: Uri, size: Dp) {
//    Image(
//        painter = rememberIconPainter(contentUri = iconUri),
//        contentDescription = null,
//        modifier = Modifier
//            .size(size)
//            .clip(CircleShape)
//            .background(Color.LightGray),
//    )
}

@Composable
private fun MessageList(
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
                if (message.senderIconUri != null) {
                    SmallContactIcon(iconUri = message.senderIconUri, size = iconSize)
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
                        Log.e("myTag", "Unrecognized media type")
                    }
                } else {
                    Log.e("myTag", "No MIME type associated with media object")
                }
            }
        }
    }
}

@Composable
private fun VideoMessagePreview(videoUri: String, onClick: () -> Unit) {
    val context = LocalContext.current.applicationContext

    // Running on an IO thread for loading metadata from remote urls to reduce lag time
    val bitmapState = produceState<Bitmap?>(initialValue = null) {
        withContext(Dispatchers.IO) {
            val mediaMetadataRetriever = MediaMetadataRetriever()

            // Remote url
            if (videoUri.contains("https://")) {
                mediaMetadataRetriever.setDataSource(videoUri, HashMap<String, String>())
            } else { // Locally saved files
                mediaMetadataRetriever.setDataSource(context, Uri.parse(videoUri))
            }
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

@Composable
private fun InputBar(
    input: String,
    contentPadding: PaddingValues,
    sendEnabled: Boolean,
    onInputChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    onCameraClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(onClick = onCameraClick) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            IconButton(onClick = onPhotoPickerClick) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = "Select Photo or video",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            TextField(
                value = input,
                onValueChange = onInputChanged,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Send,
                ),
                keyboardActions = KeyboardActions(onSend = { onSendClick() }),
                placeholder = { Text("Type your message") },
                shape = MaterialTheme.shapes.extraLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
            )
            FilledIconButton(
                onClick = onSendClick,
                modifier = Modifier.size(56.dp),
                enabled = sendEnabled,
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInputBar() {

        InputBar(
            input = "Hello, world",
            contentPadding = PaddingValues(0.dp),
            onInputChanged = {},
            onSendClick = {},
            onCameraClick = {},
            onPhotoPickerClick = {},
            sendEnabled = true,
        )
}

@Preview
@Composable
private fun PreviewChatContent() {

        ChatContent(
            name = "berkay",
            messages = listOf(
                ChatMessage("Hi!", null, null, 0L, false, null),
                ChatMessage("Hello", null, null, 0L, true, null),
                ChatMessage("world", null, null, 0L, true, null),
                ChatMessage("!", null, null, 0L, true, null),
                ChatMessage("Hello, world!", null, null, 0L, true, null),
            ),
            input = "Hello",
            sendEnabled = true,
            onBackPressed = {},
            onInputChanged = {},
            onSendClick = {},
            onCameraClick = {},
            onPhotoPickerClick = {},
            onVideoClick = {},
        )

}
