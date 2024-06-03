package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.subscribe

import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.LogService
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    logService: LogService,
): QuickChatViewModel(logService) {}
