package com.kapirti.ira.soci.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * In context of a ViewModel, converts this [Flow] into a [StateFlow] so that it is suitable for
 * use in UI.
 */
context(ViewModel)
fun <T> Flow<T>.stateInUi(
    initialValue: T,
): StateFlow<T> {
    return stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), initialValue)
}
