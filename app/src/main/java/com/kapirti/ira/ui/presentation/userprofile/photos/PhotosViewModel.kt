package com.kapirti.ira.ui.presentation.userprofile.photos

import com.kapirti.ira.model.service.LogService
import com.kapirti.ira.ui.presentation.QuickChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    logService: LogService
): QuickChatViewModel(logService) {



}
