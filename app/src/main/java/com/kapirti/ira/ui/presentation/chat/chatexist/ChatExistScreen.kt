package com.kapirti.ira.ui.presentation.chat.chatexist

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.ira.common.composable.DialogCancelButton
import com.kapirti.ira.common.composable.DialogConfirmButton
import com.kapirti.ira.core.viewmodel.IncludeChatViewModel
import com.kapirti.ira.core.viewmodel.IncludeUserIdViewModel
import com.kapirti.ira.model.Chat
import com.kapirti.ira.model.ChatMessage
import com.kapirti.ira.model.User
import com.kapirti.ira.ui.presentation.chat.ext.ChatAppBar
import com.kapirti.ira.ui.presentation.chat.ext.InputBar
import com.kapirti.ira.ui.presentation.chat.ext.LifecycleEffect
import com.kapirti.ira.ui.presentation.chat.ext.MessageList
import com.kapirti.ira.R.string as AppText


@Composable
fun ChatExistScreen(
    foreground: Boolean,
    onCameraClick: () -> Unit,
    onPhotoPickerClick: () -> Unit,
    onVideoClick: (uri: String) -> Unit,
    prefilledText: String? = null,
    popUp: () -> Unit,
    navigateUserProfile: () -> Unit,
    includeUserIdViewModel: IncludeUserIdViewModel,
    includeChatViewModel: IncludeChatViewModel,
    modifier: Modifier = Modifier,
    viewModel: ChatExistViewModel = hiltViewModel(),
){
    val options by viewModel.options
    val uiState by viewModel.uiState

    LaunchedEffect(includeChatViewModel) {
        viewModel.initialize(includeChatViewModel.chat ?: Chat())

        if (prefilledText != null) {
            viewModel.prefillInput(prefilledText)
        }
    }

    val partner by viewModel.partner.collectAsStateWithLifecycle()
    val me by viewModel.me.collectAsStateWithLifecycle()
    val input by viewModel.input.collectAsStateWithLifecycle()
    val sendEnabled by viewModel.sendEnabled.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()

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

            ChatContent(
                user = itUser,
                onBackPressed = popUp,
                options = options,
                onActionClick = { action -> viewModel.onChatActionClick(action) },
                onTopBarClick = {
                    includeUserIdViewModel.addPartnerId(uiState.partnerUid)
                    navigateUserProfile()
                },
                modifier = modifier
                    .clip(RoundedCornerShape(5)),

                messages = messages,
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
                     /**   chatId = chatId,
                        partnerName = userName,
                        partnerSurname = userSurname,
                        partnerPhoto = userPhoto,
                        partnerUid = userUid,
                        profileName = profileName,
                        profileSurname = profileSurname,
                        profilePhoto = profilePhoto,
                        profileUid = profileUid,
                        openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist*/
                    )
                },
                onCameraClick = onCameraClick,
                onPhotoPickerClick = onPhotoPickerClick,
                onVideoClick = onVideoClick,
            )




            if (viewModel.showBlockDialog.value) {
                AlertDialog(
                    title = { Text(stringResource(AppText.block)) },
                    text = { Text(stringResource(AppText.block_user)) },
                    dismissButton = { DialogCancelButton(AppText.cancel) { viewModel.showBlockDialog.value = false } },
                    confirmButton = {
                        DialogConfirmButton(AppText.block) {
                            viewModel.onBlockButtonClick(popUp, chatId = uiState.chatId,
                                name = profileName, surname = profileSurname, photo = profilePhoto,
                                partnerUid = userUid, partnerPhoto = userPhoto,
                                partnerSurname = userSurname, partnerName = userName
                            )
                            viewModel.showBlockDialog.value = false
                        }
                    },
                    onDismissRequest = { viewModel.showBlockDialog.value = false }
                )
            }
            if (viewModel.showReportDialog.value) {
                AlertDialog(
                    title = { Text(stringResource(AppText.report)) },
                    text = { Text(stringResource(AppText.report_user)) },
                    dismissButton = { DialogCancelButton(AppText.cancel) { viewModel.showReportDialog.value = false } },
                    confirmButton = {
                        DialogConfirmButton(AppText.report) {
                            viewModel.onReportButtonClick(popUp, chatId = uiState.chatId,
                                name = profileName, surname = profileSurname, photo = profilePhoto,
                                partnerUid = userUid, partnerPhoto = userPhoto,
                                partnerSurname = userSurname, partnerName = userName
                            )
                            viewModel.showReportDialog.value = false
                        }
                    },
                    onDismissRequest = { viewModel.showReportDialog.value = false }
                )
            }


        }
    }

    LifecycleEffect(
        onResume = { viewModel.setForeground(foreground) },
        onPause = { viewModel.setForeground(false) },
    )
    LaunchedEffect(viewModel) { viewModel.loadChatOptions() }
}



/**
//


    openAndPopUpChatNopeToExist: () -> Unit,
    showInterstialAd: () -> Unit,
) {









<resources>
<string name="app_name">Quick Chat</string>

<!-- QChat App -->
<string name="not_connected">⚠️You aren’t connected to the internet</string>
<string name="generic_error">Something wrong happened. Please try again.</string>
<string name="try_again">Try again</string>

<!-- Welcome Screen -->
<string name="welcome">Welcome</string>
<string name="welcome_text">Welcome to Quick Chat application</string>
<string name="enjoy">Enjoy</string>
<string name="enjoy_text">Chat with your peers</string>
<string name="join">Join</string>
<string name="join_text">Register or Sign in</string>
<string name="finish">Finish</string>

<!-- Login / Register Screen -->
<string name="register">Register</string>
<string name="log_in">Log In</string>
<string name="forgotten_password">Forgotten password? Click to get recovery email</string>
<string name="create_new_account">Create New Account</string>
<string name="email">Email</string>
<string name="email_error">Please insert a valid email.</string>
<string name="empty_password_error">Password cannot be empty.</string>
<string name="password">Password</string>
<string name="password_error">Your password should have at least six digits and include one digit, one lower case letter and one upper case letter.</string>
<string name="password_match_error">Passwords do not match.</string>
<string name="repeat_password">Repeat password</string>
<string name="recovery_email_sent">Check your inbox for the recovery email.</string>
<string name="welcome_back">Welcome back</string>
<string name="already_have_an_account">Already have an account</string>

<!-- Edit Screen -->
<string name="female">Female</string>
<string name="male">Male</string>
<string name="next">Next</string>
<string name="done">Done</string>
<string name="previous">Previous</string>
<string name="question_count">\u00A0of %d</string>
<string name="tell_us_about_you">Tell us something about yourself</string>
<string name="description">Description</string>
<string name="selfie_skills">Show off your selfie skills!</string>
<string name="feedback_description">We are constantly improving our product by listening to your feedback.</string>
<string name="in_my_free_time">In my free time I like to …</string>
<string name="select_all">Select all that apply.</string>
<string name="birthday">Birthday</string>
<string name="select_date">Select date.</string>
<string name="name">Name</string>
<string name="surname">Surname</string>
<string name="name_and_surname">Name And Surname</string>
<string name="display_name">Display name</string>
<string name="add_photo">ADD PHOTO</string>
<string name="retake_photo">RETAKE PHOTO</string>
<string name="delete_my_account">Delete my account</string>
<string name="delete_account_title">Delete account?</string>
<string name="delete_account_description">You will lose all your data and your account will be deleted. This action is irreversible.</string>
<string name="why_delete_account">why are you deleting your account</string>

<!-- Home Screen -->
<string name="no_users_all">You have no users!</string>
<string name="loading_users_error">Error while loading users</string>
<string name="jump_top">Jump to top</string>

<!-- Chats Screen -->
<string name="start_new_conversation">Life is an adventure worth exploring; gather your courage and embrace new experiences!</string>
<string name="no_chats_all">You have no chats!</string>
<string name="loading_chats_error">Error while loading chats</string>
<string name="archive">Archive</string>

<!-- Archive Screen -->
<string name="no_archives_all">You have no archives!</string>
<string name="loading_archives_error">Error while loading archives</string>
<string name="unarchive">Unarchive</string>

<!-- Chat Screen -->
<string name="online">Online</string>
<string name="jump_bottom">Jump to bottom</string>
<string name="no_messages_all">You have no messages!</string>
<string name="hello_hint">Hello</string>

<!-- Search Screen -->
<string name="search">Search</string>
<string name="search_count">%1d users</string>
<string name="search_no_matches">No matches for “%1s”</string>
<string name="search_no_matches_retry">Try broadening your search</string>
<string name="recent_searches">Recent searches</string>

<!-- User Profile Screen -->
<string name="message">Message</string>
<string name="see_more">SEE MORE</string>
<string name="see_less">SEE LESS</string>

<!-- Profile Screen -->
<string name="settings">Settings</string>
<string name="profile_not_found">Profile not found</string>
<string name="loading_profile_error">Error while loading profile</string>
<string name="gender">Gender</string>

<!-- Settings Screen -->
<string name="share">Share</string>
<string name="rate">Rate</string>
<string name="feedback">Feedback</string>
<string name="dark_mode">Dark mode</string>
<string name="sign_out">Sign out</string>
<string name="sign_out_title">Sign out?</string>
<string name="sign_out_description">You will have to sign in again to see your data.</string>
<string name="cancel">Cancel</string>
</resources>





<!--

<string name="avatar">Avatar</string>

<string name="select_one">Select one.</string>
<string name="select_your_avatar">Select your avatar.</string>

<string name="cd_send">Send</string>
doneeeeeeeeeeeed
-->


<!--
<string name="label_all">All Periods</string>
<string name="no_periods_all">You have no periods!</string>
<string name="loading_periods_error">Error while loading periods</string>
<string name="no_data">No data</string>
<string name="period_not_found">Period not found</string>
<string name="empty_period_message">Periods cannot be empty</string>
<string name="add_period">New Period</string>
<string name="description_hint">Enter your period here.</string>
<string name="period_details">Period Details</string>



<string name="selfies">How do you feel about selfies 🤳?</string>

<string name="author_me">me</string>
<string name="now">8:30 PM</string>
<string name="not_available">Functionality currently not available</string>
<string name="not_available_subtitle">Grab a beverage and check back later!</string>
<string name="emoji_selector_desc">Emoji selector</string>
<string name="emoji_selector_bt_desc">Show Emoji selector</string>
<string name="dm_desc">Direct Message</string>
<string name="videochat_desc">Start videochat</string>
<string name="send">Send</string>
<string name="emojis_label">Emojis</string>
<string name="stickers_label">Stickers</string>
<string name="textfield_hint">Message #composers</string>
<string name="textfield_desc">Text input</string>
<string name="map_selector_desc">Location selector</string>
<string name="attach_photo_desc">Attach Photo</string>
<string name="attached_image">Attached image</string>


<string name="label_add">Add to cart</string>
<string name="label_search">Perform search</string>





<string name="app_tagline">Better surveys with Jetpack Compose</string>
<string name="confirm_password">Confirm password</string>
<string name="sign_in">Sign in</string>
<string name="sign_in_guest">Sign in as guest</string>
<string name="sign_in_create_account">Sign in or create an account</string>
<string name="or">or</string>
<string name="forgot_password">Forgot password?</string>
<string name="user_continue">Continue</string>
<string name="create_account">Create account</string>
<string name="terms_and_conditions">By continuing, you agree to our Terms of Service. We’ll
handle your data according to our Privacy Policy.</string>
<string name="feature_not_available">Feature not available</string>
<string name="dismiss">Dismiss</string>

<string name="back">Back</string>
<string name="show_password">Show password</string>
<string name="hide_password">Hide password</string>


<string name="in_my_free_time">In my free time I like to …</string>
<string name="read">Read</string>
<string name="work_out">Work out</string>
<string name="draw">Draw</string>
<string name="play_games">Play video games</string>
<string name="dance">Dance</string>
<string name="watch_movies">Watch movies</string>

<string name="spark">Spark</string>
<string name="lenz">Lenz</string>
<string name="bugchaos">Bug of Chaos</string>
<string name="frag">Frag</string>

<string name="takeaway">When was the last time you ordered takeaway because you couldn\'t be bothered to cook?</string>

<string name="strongly_dislike">Strongly\nDislike</string>
<string name="strongly_like">Strongly\nLike</string>
<string name="neutral">Neutral</string>
</
<string name="navigation_drawer_open">Open navigation drawer</string>
<string name="navigation_drawer_close">Close navigation drawer</string>
<string name="nav_header_title">Composer</string>
<string name="nav_header_subtitle">android.studio@android.com</string>
<string name="nav_header_desc">Navigation header</string>
<string name="action_settings">Settings</string>
<string name="menu_home">Home</string>
<string name="menu_gallery">Gallery</string>
<string name="menu_slideshow">Slideshow</string>
<string name="conversations">Conversations</string>
<string name="profile">Profile</string>
<string name="members">%d members</string>
<string name="edit_profile">Edit Profile</string>
<string name="profile_error">There was an error loading the profile</string>
<string name="bio">Bio</string>
<string name="status">Status</string>
<string name="timezone">Timezone</string>
<string name="twitter">Twitter</string>
<string name="common_channels">Channels in common</string>
<string name="lorem">Lorem or Ipsum</string>
<string name="search">Search</string>
<string name="info">Information</string>
<string name="more_options">More options</string>
-->






}
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatContent(
    user: User,
    onBackPressed: (() -> Unit)?,
    options: List<String>,
    onTopBarClick: () -> Unit,
    onActionClick: (String) -> Unit,
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
                options = options,
                onTopBarClick = onTopBarClick,
                onActionClick = onActionClick,
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









//import com.zepi.social_chat_food.soci.data.ChatWithLastMessage
//import com.zepi.social_chat_food.soci.model.ChatDetail









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
