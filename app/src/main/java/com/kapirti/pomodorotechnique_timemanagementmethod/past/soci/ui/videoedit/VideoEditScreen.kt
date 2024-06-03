package com.kapirti.pomodorotechnique_timemanagementmethod.past.soci.ui.videoedit

import androidx.compose.runtime.Composable
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController

private const val TAG = "VideoEditScreen"

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun VideoEditScreen(
    chatId: Long,
    uri: String,
    onCloseButtonClicked: () -> Unit,
    navController: NavController,
) {
}/**
    val context = LocalContext.current

    val viewModel: VideoEditScreenViewModel = hiltViewModel()
    viewModel.setChatId(chatId)

    val isFinishedEditing = viewModel.isFinishedEditing.collectAsStateWithLifecycle()
    if (isFinishedEditing.value) {
    navController.popBackStack("chat/$chatId", false)
    }

    val isProcessing = viewModel.isProcessing.collectAsState()

    var removeAudioEnabled by rememberSaveable { mutableStateOf(false) }
    var overlayText by rememberSaveable { mutableStateOf("") }
    var redOverlayTextEnabled by rememberSaveable { mutableStateOf(false) }
    var largeOverlayTextEnabled by rememberSaveable { mutableStateOf(false) }

    Scaffold(
    topBar = {
    VideoEditTopAppBar(
    onSendButtonClicked = {
    viewModel.applyVideoTransformation(
    context = context,
    videoUri = uri,
    removeAudio = removeAudioEnabled,
    textOverlayText = overlayText,
    textOverlayRedSelected = redOverlayTextEnabled,
    textOverlayLargeSelected = largeOverlayTextEnabled,
    )
    },
    onCloseButtonClicked = onCloseButtonClicked,
    )
    },
    ) { innerPadding ->
    Column(
    modifier = Modifier
    .padding(innerPadding)
    .background(Color.Black)
    .fillMaxSize()
    .imePadding()
    .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally,
    ) {
    Spacer(modifier = Modifier.height(50.dp))
    VideoMessagePreview(uri, isProcessing.value)
    Spacer(modifier = Modifier.height(20.dp))

    Column(
    modifier = Modifier
    .padding(15.dp)
    .background(
    color = colorResource(R.color.dark_gray),
    shape = RoundedCornerShape(size = 28.dp),
    )
    .padding(15.dp),
    ) {
    VideoEditFilterChip(
    icon = Icons.Filled.VolumeMute,
    selected = removeAudioEnabled,
    onClick = { removeAudioEnabled = !removeAudioEnabled },
    label = stringResource(id = R.string.remove_audio),
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextOverlayOption(
    inputtedText = overlayText,
    inputtedTextChange = {
    // Limit character count to 20
    if (it.length <= 20) {
    overlayText = it
    }
    },
    redTextCheckedState = redOverlayTextEnabled,
    redTextCheckedStateChange = {
    redOverlayTextEnabled = !redOverlayTextEnabled
    },
    largeTextCheckedState = largeOverlayTextEnabled,
    largeTextCheckedStateChange = {
    largeOverlayTextEnabled = !largeOverlayTextEnabled
    },
    )
    }
    }
    }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun VideoEditTopAppBar(
    onSendButtonClicked: () -> Unit,
    onCloseButtonClicked: () -> Unit,
    ) {
    TopAppBar(
    title = {},
    colors = TopAppBarDefaults.topAppBarColors(
    containerColor = Color.Black,
    navigationIconContentColor = Color.White,
    ),
    navigationIcon = {
    IconButton(onClick = onCloseButtonClicked) {
    Icon(
    imageVector = Icons.Default.Close,
    contentDescription = stringResource(R.string.back),
    )
    }
    },
    actions = {
    Button(
    colors = ButtonDefaults.buttonColors(
    containerColor = colorResource(R.color.aqua),
    contentColor = Color.Black,
    ),
    onClick = onSendButtonClicked,
    modifier = Modifier.padding(8.dp),
    ) {
    Text(text = stringResource(id = R.string.send))
    }
    },
    )
    }

    @Composable
    private fun VideoMessagePreview(videoUri: String, isProcessing: Boolean) {
    // Render yellow box instead of frame of captured video for Preview purposes
    if (LocalInspectionMode.current) {
    Box(
    modifier = Modifier
    .width(200.dp)
    .height(266.dp)
    .background(color = Color.Yellow),
    )
    return
    }

    val mediaMetadataRetriever = MediaMetadataRetriever()
    mediaMetadataRetriever.setDataSource(LocalContext.current, Uri.parse(videoUri))

    // Return any frame that the framework considers representative of a valid frame
    val bitmap = mediaMetadataRetriever.frameAtTime

    if (bitmap != null) {
    Box(
    modifier = Modifier
    .padding(10.dp),
    ) {
    Image(
    modifier = Modifier.width(200.dp),
    bitmap = bitmap.asImageBitmap(),
    contentDescription = null,
    )

    Icon(
    Icons.Filled.Movie,
    tint = Color.White,
    contentDescription = null,
    modifier = Modifier
    .size(60.dp)
    .padding(10.dp),
    )

    if (isProcessing) {
    CircularProgressIndicator(
    modifier = Modifier.align(Alignment.Center)
    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
    .padding(8.dp),
    )
    }
    }
    } else {
    Log.e(TAG, "Error rendering preview of video")
    }
    }

    @Composable
    fun TextOverlayOption(
    inputtedText: String,
    inputtedTextChange: (String) -> Unit,
    redTextCheckedState: Boolean,
    redTextCheckedStateChange: () -> Unit,
    largeTextCheckedState: Boolean,
    largeTextCheckedStateChange: () -> Unit,
    ) {
    Column(
    modifier = Modifier
    .fillMaxWidth(),
    ) {
    TextField(
    value = inputtedText,
    onValueChange = inputtedTextChange,
    placeholder = { Text(stringResource(R.string.add_text_overlay_placeholder)) },
    modifier = Modifier.width(200.dp),
    colors = TextFieldDefaults.colors(
    unfocusedContainerColor = Color.DarkGray,
    unfocusedPlaceholderColor = Color.LightGray,
    ),
    )
    Spacer(modifier = Modifier.padding(5.dp))
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
    VideoEditFilterChip(
    icon = Icons.Filled.DonutLarge,
    selected = redTextCheckedState,
    onClick = redTextCheckedStateChange,
    label = stringResource(id = R.string.red_text_option),
    iconColor = Color.Red,
    )
    Spacer(modifier = Modifier.padding(10.dp))

    VideoEditFilterChip(
    icon = Icons.Filled.FormatSize,
    selected = largeTextCheckedState,
    onClick = largeTextCheckedStateChange,
    label = stringResource(id = R.string.large_text_option),
    )
    }
    }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun VideoEditFilterChip(
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    iconColor: Color = Color.White,
    selectedIconColor: Color = Color.Black,
    ) {
    FilterChip(
    leadingIcon = {
    Icon(
    imageVector = icon,
    contentDescription = null,
    modifier = Modifier.size(FilterChipDefaults.IconSize),
    )
    },
    selected = selected,
    onClick = onClick,
    label = { Text(label) },
    colors = FilterChipDefaults.filterChipColors(
    labelColor = Color.White,
    selectedContainerColor = colorResource(id = R.color.light_purple),
    selectedLabelColor = Color.Black,
    iconColor = iconColor,
    selectedLeadingIconColor = selectedIconColor,
    ),
    )
    }

    @Composable
    @Preview
    fun VideoEditScreenPreview() {
    VideoEditScreen(
    chatId = 0L,
    uri = "",
    onCloseButtonClicked = {},
    navController = rememberNavController(),
    )
    }
     */
