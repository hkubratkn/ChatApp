package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation
/**
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class QuickChatViewModel(private val logService: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.LogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) { }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}
*/
