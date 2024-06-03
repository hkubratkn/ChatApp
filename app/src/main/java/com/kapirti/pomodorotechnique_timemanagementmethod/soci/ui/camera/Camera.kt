package com.kapirti.pomodorotechnique_timemanagementmethod.soci.ui.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Camera(
    chatId: Long,
    onMediaCaptured: (Media?) -> Unit,
    modifier: Modifier = Modifier,
  //  viewModel: CameraViewModel = hiltViewModel(),
) {}/**
    var surfaceProvider by remember { mutableStateOf<Preview.SurfaceProvider?>(null) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    var captureMode by remember { mutableStateOf(CaptureMode.PHOTO) }
    val cameraAndRecordAudioPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        ),
    )

    viewModel.setChatId(chatId)

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    var isLayoutUnfolded by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(lifecycleOwner, context) {
        val windowInfoTracker = WindowInfoTracker.getOrCreate(context)
        windowInfoTracker.windowLayoutInfo(context).collect { newLayoutInfo ->
            try {
                val foldingFeature = newLayoutInfo?.displayFeatures
                    ?.firstOrNull { it is FoldingFeature } as FoldingFeature
                isLayoutUnfolded = (foldingFeature != null)
            } catch (e: Exception) {
                // If there was an issue detecting a foldable in the open position, default
                // to isLayoutUnfolded being false.
                isLayoutUnfolded = false
            }
        }
    }

    val viewFinderState by viewModel.viewFinderState.collectAsStateWithLifecycle()
    var rotation by remember { mutableStateOf(Surface.ROTATION_0) }

    DisposableEffect(lifecycleOwner, context) {
        val rotationProvider = RotationProvider(context)
        val rotationListener: (Int) -> Unit = { rotationValue: Int ->
            if (rotationValue != rotation) {
                surfaceProvider?.let { provider ->
                    viewModel.setTargetRotation(
                        rotationValue,
                    )
                }
            }
            rotation = rotationValue
        }

        rotationProvider.addListener(Dispatchers.Main.asExecutor(), rotationListener)

        onDispose {
            rotationProvider.removeListener(rotationListener)
        }
    }

    val onPreviewSurfaceProviderReady: (Preview.SurfaceProvider) -> Unit = {
        surfaceProvider = it
        viewModel.startPreview(lifecycleOwner, it, captureMode, cameraSelector, rotation)
    }

    fun setCaptureMode(mode: CaptureMode) {
        captureMode = mode
        surfaceProvider?.let { provider ->
            viewModel.startPreview(
                lifecycleOwner,
                provider,
                captureMode,
                cameraSelector,
                rotation,
            )
        }
    }

    fun setCameraSelector(selector: CameraSelector) {
        cameraSelector = selector
        surfaceProvider?.let { provider ->
            viewModel.startPreview(
                lifecycleOwner,
                provider,
                captureMode,
                cameraSelector,
                rotation,
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun onVideoRecordingStart() {
        captureMode = CaptureMode.VIDEO_RECORDING
        viewModel.startVideoCapture(onMediaCaptured)
    }

    fun onVideoRecordingFinish() {
        captureMode = CaptureMode.VIDEO_READY
        viewModel.saveVideo()
    }

    if (cameraAndRecordAudioPermissionState.allPermissionsGranted) {
        Box(modifier = modifier.background(color = Color.Black)) {
            Column(verticalArrangement = Arrangement.Bottom) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 25.dp, 0.dp, 0.dp)
                        .background(Color.Black)
                        .height(50.dp),
                ) {
                    IconButton(onClick = {
                        onMediaCaptured(null)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }
                if (isLayoutUnfolded != null) {
                    if (isLayoutUnfolded as Boolean) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    CameraControls(
                                        captureMode,
                                        { setCaptureMode(CaptureMode.PHOTO) },
                                        { setCaptureMode(CaptureMode.VIDEO_READY) },
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 5.dp, 0.dp, 50.dp)
                                        .background(Color.Black)
                                        .height(100.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    ShutterButton(
                                        captureMode,
                                        { viewModel.capturePhoto(onMediaCaptured) },
                                        { onVideoRecordingStart() },
                                        { onVideoRecordingFinish() },
                                    )
                                }
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    CameraSwitcher(captureMode, cameraSelector, ::setCameraSelector)
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(0.9F)
                                    .weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                ViewFinder(
                                    viewFinderState.cameraState,
                                    onPreviewSurfaceProviderReady,
                                    viewModel::setZoomScale,
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        ) {
                            ViewFinder(
                                viewFinderState.cameraState,
                                onPreviewSurfaceProviderReady,
                                viewModel::setZoomScale,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp, 0.dp, 5.dp)
                                .background(Color.Black)
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            CameraControls(
                                captureMode,
                                { setCaptureMode(CaptureMode.PHOTO) },
                                { setCaptureMode(CaptureMode.VIDEO_READY) },
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp, 0.dp, 50.dp)
                                .background(Color.Black)
                                .height(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Spacer(modifier = Modifier.size(50.dp))
                            ShutterButton(
                                captureMode,
                                { viewModel.capturePhoto(onMediaCaptured) },
                                { onVideoRecordingStart() },
                                { onVideoRecordingFinish() },
                            )
                            CameraSwitcher(captureMode, cameraSelector, ::setCameraSelector)
                        }
                    }
                }
            }
        }
    } else {
        CameraAndRecordAudioPermission(cameraAndRecordAudioPermissionState) {
            onMediaCaptured(null)
        }
    }
}

@Composable
fun CameraControls(captureMode: CaptureMode, onPhotoButtonClick: () -> Unit, onVideoButtonClick: () -> Unit) {
    val activeButtonColor =
        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    val inactiveButtonColor =
        ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    if (captureMode != CaptureMode.VIDEO_RECORDING) {
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = onPhotoButtonClick,
            colors = if (captureMode == CaptureMode.PHOTO) activeButtonColor else inactiveButtonColor,
        ) {
            Text("Photo")
        }
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = onVideoButtonClick,
            colors = if (captureMode != CaptureMode.PHOTO) activeButtonColor else inactiveButtonColor,
        ) {
            Text("Video")
        }
    }
}

@Composable
fun ShutterButton(captureMode: CaptureMode, onPhotoCapture: () -> Unit, onVideoRecordingStart: () -> Unit, onVideoRecordingFinish: () -> Unit) {
    Box(modifier = Modifier.padding(25.dp, 0.dp)) {
        if (captureMode == CaptureMode.PHOTO) {
            Button(
                onClick = onPhotoCapture,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp),
            ) {}
        } else if (captureMode == CaptureMode.VIDEO_READY) {
            Button(
                onClick = onVideoRecordingStart,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp),
            ) {}
        } else if (captureMode == CaptureMode.VIDEO_RECORDING) {
            Button(
                onClick = onVideoRecordingFinish,
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp),
            ) {}
            Spacer(modifier = Modifier.width(100.dp))
        }
    }
}

@Composable
fun CameraSwitcher(captureMode: CaptureMode, cameraSelector: CameraSelector, setCameraSelector: KFunction1<CameraSelector, Unit>) {
    if (captureMode != CaptureMode.VIDEO_RECORDING) {
        IconButton(onClick = {
            if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                setCameraSelector(CameraSelector.DEFAULT_FRONT_CAMERA)
            } else {
                setCameraSelector(CameraSelector.DEFAULT_BACK_CAMERA)
            }
        }) {
            Icon(
                imageVector = Icons.Default.Autorenew,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .height(75.dp)
                    .width(75.dp),
            )
        }
    }
}
*/
