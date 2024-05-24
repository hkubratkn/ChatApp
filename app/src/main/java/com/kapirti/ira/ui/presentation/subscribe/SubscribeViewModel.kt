package com.kapirti.ira.ui.presentation.subscribe

import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    logService: LogService,
): ZepiViewModel(logService) {}
