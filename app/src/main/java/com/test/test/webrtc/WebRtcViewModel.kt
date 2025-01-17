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

package com.test.test.webrtc

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import com.test.test.webrtc.peer.StreamPeerConnectionFactory
import com.test.test.webrtc.sessions.WebRtcSessionManager
import com.test.test.webrtc.sessions.WebRtcSessionManagerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WebRtcViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private var sessionManager: WebRtcSessionManager = WebRtcSessionManagerImpl(
        context = context,
        signalingClient = SignalingClient(),
        peerConnectionFactory = StreamPeerConnectionFactory(context)
    )

    val sessionStateFlow = sessionManager.signalingClient.sessionStateFlow

    fun setRoomId(roomId: String) = viewModelScope.launch {
        val chatRoom = firestoreService.getChatRoom(roomId)
        val myId = firebaseAuth.currentUser!!.uid
        val otherUserId = chatRoom!!.userIds.filterNot { it == myId }.first()
        android.util.Log.d("myTag", "other user id in webRtcSession : $otherUserId")
    }


}
