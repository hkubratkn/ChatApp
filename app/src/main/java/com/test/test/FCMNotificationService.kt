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

package com.test.test

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.test.model.ChatMessage
import com.test.test.model.User
import com.test.test.ui.presentation.notification.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FCMNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //TODO : Update fcmToken in Firestore
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        android.util.Log.d("myTag","FCM message received")
        android.util.Log.d("myTag","message from : ${message.from}")

        // Check if message contains a data payload.
        if (message.data.isNotEmpty()) {
            android.util.Log.d("myTag", "Message data payload: ${message.data}")
            android.util.Log.d("myTag", "Message data payload: ${message.data.get("theSender")}")

            // Check if data needs to be processed by long running job
//            if (needsToBeScheduled()) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
        }

        // Check if message contains a notification payload.
        message.notification?.let {
            android.util.Log.d("myTag", "Message Notification Body: ${it.body}")

            notificationHelper.showNotification(
                User(
                    id = message.data.get("theSender").orEmpty(),
                    name = it.title.orEmpty(),
                ),
                listOf(ChatMessage(message = it.body!!)), true,
            )
        }

        //val userId = getIntent().getExtras().getString("userId")

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.



        //notificationHelper.showNotification()

    }

}
