/*
 * Copyright (C) 2025 The Android Open Source Project
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

package com.test.test.ui.presentation.calls

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.service.FirestoreService
import com.test.test.ui.presentation.conversations.ConversationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class CallHistoryViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestoreService: FirestoreService,
) : ViewModel() {

    var uiState = mutableStateOf(CallHistoryUiState())
        private set

    init {
        fetchCallHistory()
    }

    private fun fetchCallHistory() = viewModelScope.launch {
        val myId = firebaseAuth.currentUser?.uid.orEmpty()
        firestoreService.getCallRecords(myId).collectLatest { records ->
            uiState.value = uiState.value.copy(
                callRecords = records.map {
                    RegisteredCall(
                        //id = it.userId
                        source = it.peerName,
                        callType = it.callType,
                        duration = (it.callEnd!!.seconds - it.callStart!!.seconds).toDuration(DurationUnit.SECONDS)   //Duration.parse(it.callEnd!!.toDate().toString()) - Duration.parse(it.callStart!!.toDate().toString())  //Duration. between(it.callEnd!!.get, it.callStart!!)
                    )
                }
            )

        }


    }



}
