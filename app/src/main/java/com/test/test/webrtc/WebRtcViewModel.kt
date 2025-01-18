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
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import com.test.test.ui.presentation.chats.ChatUiState
import com.test.test.webrtc.peer.StreamPeerConnectionFactory
import com.test.test.webrtc.sessions.WebRtcSessionManager
import com.test.test.webrtc.sessions.WebRtcSessionManagerImpl
import com.test.test.webrtc.ui.WebRtcUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class WebRtcViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    private val savedStateRoomId: String? = savedStateHandle.get<String>("roomId")


    var uiState = mutableStateOf(WebRtcUiState())
        private set

    var sessionManager: WebRtcSessionManager = WebRtcSessionManagerImpl(
        context = context,
        signalingClient = SignalingClient(
            uId = firebaseAuth.currentUser?.uid.orEmpty(),
            roomId = savedStateRoomId.orEmpty()
        ),
        peerConnectionFactory = StreamPeerConnectionFactory(context)
    )

    val sessionStateFlow = sessionManager.signalingClient.sessionStateFlow

    init {
        savedStateRoomId?.let { setRoomId(roomId = it) }
    }

    private fun setRoomId(roomId: String) = viewModelScope.launch {
        android.util.Log.d("myTag","saved state room id : $savedStateRoomId")
        val chatRoom = firestoreService.getChatRoom(roomId)
        val myId = firebaseAuth.currentUser!!.uid
        val otherUserId = chatRoom!!.userIds.filterNot { it == myId }.first()
        android.util.Log.d("myTag", "other user id in webRtcSession : $otherUserId")
        val otherUser = firestoreService.getUser(otherUserId)



//        sessionManager = WebRtcSessionManagerImpl(
//            context = context,
//            signalingClient = SignalingClient(),
//            peerConnectionFactory = StreamPeerConnectionFactory(context)
//        )

//        sessionManager.signalingClient.sessionStateFlow.collectLatest {
//            android.util.Log.d("myTag","rtc session state : $it")
//            uiState.value = uiState.value.copy(
//                webRTCSessionState = it
//            )
//        }

        sessionManager.signalingClient.setPeers(myId, otherUserId)

        uiState.value = uiState.value.copy(
            chatRoom = chatRoom,
            otherUserName = otherUser?.name.orEmpty(),
            //uiSessionManager = sessionManager
        )



    }

    fun onDestory() {
        sessionManager.disconnect()
    }

}
