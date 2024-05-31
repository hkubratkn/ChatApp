/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kapirti.ira.ui.presentation.chat.chatnope


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.model.ChatMessage
import com.kapirti.ira.model.User
import com.kapirti.ira.ui.presentation.chat.ext.ChatAppBar
import com.kapirti.ira.ui.presentation.chat.ext.InputBar
import com.kapirti.ira.ui.presentation.chat.ext.LifecycleEffect
import com.kapirti.ira.ui.presentation.chat.ext.MessageList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatNopeScreen(

//    chatId: Long,
    foreground: Boolean,
    onCameraClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    onVideoClick: (uri: String) -> Unit,
    prefilledText: String? = null,


    popUp: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    openAndPopUpChatNopeToExist: () -> Unit,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatNopeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getPartnerInfo(includeUserIdViewModel.partnerId ?: "")
        if (prefilledText != null) {
            viewModel.prefillInput(prefilledText)
        }
    }

    val partner by viewModel.partner.collectAsStateWithLifecycle()
    val me by viewModel.me.collectAsStateWithLifecycle()
    val input by viewModel.input.collectAsStateWithLifecycle()
    val sendEnabled by viewModel.sendEnabled.collectAsStateWithLifecycle()


    partner?.let { itUser ->
        me?.let { itMe ->

            val userName = itUser.name
            val userSurname = itUser.surname
            val userPhoto = itUser.photo
            val userUid = itUser.uid

            val profileName = itMe.name
            val profileSurname = itMe.surname
            val profilePhoto = itMe.photo
            val profileUid = itMe.uid

            val chatId = "${profileUid}${userUid}"

            ChatContent(
                user = itUser,
                onBackPressed = popUp,
                modifier = modifier
                    .clip(RoundedCornerShape(5)),

                messages = emptyList(),
                input = input,
                sendEnabled = sendEnabled,
                onInputChanged = { viewModel.updateInput(it) },
                onSendClick = {
                    /**            showInterstialAd()
                    includeChatViewModel.addChat(
                    Chat(
                    chatId = chatId,
                    partnerName = userName,
                    partnerSurname = userSurname,
                    partnerPhoto = userPhoto,
                    partnerUid = userUid,
                    date = Timestamp.now()
                    )
                    )*/
                    viewModel.send(
                        chatId = chatId,
                        partnerName = userName,
                        partnerSurname = userSurname,
                        partnerPhoto = userPhoto,
                        partnerUid = userUid,
                        profileName = profileName,
                        profileSurname = profileSurname,
                        profilePhoto = profilePhoto,
                        profileUid = profileUid,
                        openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist
                    )
                },
                onCameraClick = onCameraClick,
                onPhotoPickerClick = onPhotoPickerClick,
                onVideoClick = onVideoClick,
            )
        }
    }

    LifecycleEffect(
        onResume = { viewModel.setForeground(foreground) },
        onPause = { viewModel.setForeground(false) },
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatContent(
    user: User,
    onBackPressed: (() -> Unit)?,
    messages: List<ChatMessage>,
    input: String,
    sendEnabled: Boolean,
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
                user = user,
                scrollBehavior = scrollBehavior,
                onBackPressed = onBackPressed,
                onTopBarClick = {},
                onActionClick = {},
                options = emptyList(),
            )
        },
        bottomBar = {
            InputBar(
                input = input,
                onInputChanged = onInputChanged,
                onSendClick = onSendClick,
                onCameraClick = onCameraClick,
                onPhotoPickerClick = onPhotoPickerClick,
                contentPadding = PaddingValues(0.dp),
                sendEnabled = sendEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime.exclude(WindowInsets.navigationBars)),
            )
        }
    ) { innerPadding ->
        Column {
            val layoutDirection = LocalLayoutDirection.current
            MessageList(
                me = User(),
                partner = User(),
                messages = messages,
                contentPadding = innerPadding.copy(layoutDirection, bottom = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                //onVideoClick = onVideoClick,
            )
            InputBar(
                input = input,
                onInputChanged = onInputChanged,
                onSendClick = onSendClick,
                onCameraClick = onCameraClick,
                onPhotoPickerClick = onPhotoPickerClick,
                contentPadding = PaddingValues(0.dp),
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






/*




  LaunchedEffect(Unit) {
        viewModel.initialize(
            userUid = includeUserUidViewModel.userUid ?: "",
        )
    }


    val user = viewModel.user.collectAsStateWithLifecycle()
    val me = viewModel.me.collectAsStateWithLifecycle()


    Scaffold (
        topBar = {
            Column{
                AdsBannerToolbar(ads = ADS_CHAT_BANNER_ID)
                UserTopBar(title = user.value.name, online = user.value.online, startAction = { popUp() })
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                TextField(
                    value = viewModel.text ?: "",
                    onValueChange = viewModel::onTextChange,
                    placeholder = { Text("Say Hello") },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(Icons.Default.Send, null, tint = Color.Green)
                }
            }
        }
    ){ innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.BackHand, null, tint = Color.Yellow, modifier = Modifier.size(50.dp))
            Spacer(Modifier.height(20.dp))
            Text("start conversation")
        }
    }
}














package com.kapirti.video_food_delivery_shopping.ui.presentation.chat.dontknow

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

//import com.zepi.social_chat_food.soci.data.ChatWithLastMessage
//import com.zepi.social_chat_food.soci.model.ChatDetail

LaunchedEffect(chatId) {
    viewModel.setChatId(chatId)
    if (prefilledText != null) {
        viewModel.prefillInput(prefilledText)
    }
}

}*/






//done

/**
LaunchedEffect(chatId) {
viewModel.setChatId(chatId)
if (prefilledText != null) {
viewModel.prefillInput(prefilledText)
}
}
//val chat by viewModel.chat.collectAsStateWithLifecycle()
val messages by viewModel.messages.collectAsStateWithLifecycle()
val input by viewModel.input.collectAsStateWithLifecycle()
val sendEnabled by viewModel.sendEnabled.collectAsStateWithLifecycle()
chat?.let { c ->
ChatContent(
messages = messages,
input = input,
sendEnabled = sendEnabled,
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
LifecycleEffect(
onResume = { viewModel.setForeground(foreground) },
onPause = { viewModel.setForeground(false) },
)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatContent(
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
user = User(),
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
contentPadding = PaddingValues(0.dp),
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
placeholder = { Text(stringResource(R.string.message)) },
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
ZepiTheme {
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
}

@Preview
@Composable
private fun PreviewChatContent() {
ZepiTheme {
ChatContent(
chat = ChatDetail(ChatWithLastMessage(0L), listOf(Contact.CONTACTS[0])),
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
}

}*/
/**
LaunchedEffect(chatId) {
viewModel.setChatId(chatId)
if (prefilledText != null) {
viewModel.prefillInput(prefilledText)
}
}
//val chat by viewModel.chat.collectAsStateWithLifecycle()
val messages by viewModel.messages.collectAsStateWithLifecycle()
val input by viewModel.input.collectAsStateWithLifecycle()
val sendEnabled by viewModel.sendEnabled.collectAsStateWithLifecycle()
chat?.let { c ->
ChatContent(
messages = messages,
input = input,
sendEnabled = sendEnabled,
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
LifecycleEffect(
onResume = { viewModel.setForeground(foreground) },
onPause = { viewModel.setForeground(false) },
)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatContent(
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
user = User(),
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
contentPadding = PaddingValues(0.dp),
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
placeholder = { Text(stringResource(R.string.message)) },
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
ZepiTheme {
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
}

@Preview
@Composable
private fun PreviewChatContent() {
ZepiTheme {
ChatContent(
chat = ChatDetail(ChatWithLastMessage(0L), listOf(Contact.CONTACTS[0])),
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
}

}*/

/**
LaunchedEffect(Unit) {
viewModel.initialize(
userUid = includeUserUidViewModel.userUid ?: "",
)
}


val user = viewModel.user.collectAsStateWithLifecycle()
val me = viewModel.me.collectAsStateWithLifecycle()

val userName = user.value.name
val userSurname = user.value.surname
val userPhoto = user.value.photo
val userUid = user.value.uid

val profileName = me.value.name
val profileSurname = me.value.surname
val profilePhoto = me.value.photo

val chatId = "${viewModel.uid}${user.value.uid}"

Scaffold (
topBar = {
Column{
AdsBannerToolbar(ads = ADS_CHAT_BANNER_ID)
UserTopBar(title = user.value.name, online = user.value.online, startAction = { popUp() })
}
},
bottomBar = {
Row(
modifier = Modifier.fillMaxWidth()
){
TextField(
value = viewModel.text ?: "",
onValueChange = viewModel::onTextChange,
placeholder = { Text("Say Hello") },
modifier = Modifier.weight(1f)
)

IconButton(
onClick = {
showInterstialAd()
includeChatViewModel.addChat(
Chat(
chatId = chatId,
partnerName = userName,
partnerSurname = userSurname,
partnerPhoto = userPhoto,
partnerUid = userUid,
date = Timestamp.now()
)
)
viewModel.onSendClick(
who = viewModel.uid,
chatId = chatId,
partnerName = userName,
partnerSurname = userSurname,
partnerPhoto = userPhoto,
partnerUid = userUid,
profileName = profileName,
profileSurname = profileSurname,
profilePhoto = profilePhoto,
openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist
)
}
) {
Icon(Icons.Default.Send, null, tint = Color.Green)
}
}
}
){ innerPadding ->
Column(
modifier = modifier
.fillMaxSize()
.padding(innerPadding),
verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally
) {
Icon(Icons.Default.BackHand, null, tint = Color.Yellow, modifier = Modifier.size(50.dp))
Spacer(Modifier.height(20.dp))
Text("start conversation")
}
}
}

*/
