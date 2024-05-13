package com.zepi.social_chat_food.iraaa.ui.presentation.subscribe

import com.zepi.social_chat_food.iraaa.model.service.LogService
import com.zepi.social_chat_food.iraaa.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    logService: LogService,
): QChatViewModel(logService) {}
