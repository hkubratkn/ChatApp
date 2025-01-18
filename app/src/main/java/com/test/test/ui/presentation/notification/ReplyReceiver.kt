/*
 * Copyright (C) 2023 The Android Open Source Project
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

package com.test.test.ui.presentation.notification

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.ChatMessage
import com.test.test.model.service.FirestoreService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Handles the "Reply" action in the chat notification.
 */
@AndroidEntryPoint
class ReplyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firestoreService: FirestoreService

    companion object {
        const val KEY_TEXT_REPLY = "reply"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val results = RemoteInput.getResultsFromIntent(intent) ?: return
        // The message typed in the notification reply.
        val input = results.getCharSequence(KEY_TEXT_REPLY)?.toString()
        val uri = intent.data ?: return
        val chatId = uri.lastPathSegment ?: return

        if (!input.isNullOrBlank()) {
            val pendingResult = goAsync()
            val job = Job()
            CoroutineScope(job).launch {
                try {
                    //repository.sendMessage(chatId, input.toString(), null, null)
                    val myId = firebaseAuth.currentUser!!.uid
//
//                    val room = uiState.value.chatRoom
//                    room?.lastMessageTime = Timestamp.now()
//                    room?.lastMessageSenderId = myId
//                    room?.lastMessage = input
//                    room?.let {
//                        firestoreService.setChatRoom(room.id, room)

                    val chatMessage = ChatMessage(chatId, input, "", "", myId, Timestamp.now())
                    firestoreService.getChatRoomMessageReference(chatId).add(chatMessage)
                        .addOnSuccessListener {

                        }


                    // We should update the notification so that the user can see that the reply has
                    // been sent.
                    //repository.updateNotification(chatId)

                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
