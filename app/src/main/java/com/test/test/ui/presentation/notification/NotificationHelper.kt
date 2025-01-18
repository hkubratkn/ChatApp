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

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.content.LocusIdCompat
import androidx.core.content.getSystemService
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.google.firebase.Timestamp
import com.test.test.MainActivity
import com.test.test.R
import com.test.test.model.ChatMessage
import com.test.test.model.User
import com.test.test.webrtc.ui.WebRtcActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Represents a reason why a shortcut should be pushed.
 */
enum class PushReason {
    IncomingMessage,
    OutgoingMessage,
}

/**
 * Handles all operations related to [Notification].
 */
@Singleton
class NotificationHelper @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        /**
         * The notification channel for messages. This is used for showing Bubbles.
         */
        private const val CHANNEL_NEW_MESSAGES = "new_messages"
        private const val CHANNEL_CALL = "calls"

        private const val REQUEST_CONTENT = 1
        private const val REQUEST_BUBBLE = 2
        private const val REQUEST_CALL = 3
    }

    private val appContext = context.applicationContext

    private val notificationManager: NotificationManager =
        context.getSystemService() ?: throw IllegalStateException()

    fun setUpNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        if (notificationManager.getNotificationChannel(CHANNEL_NEW_MESSAGES) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_NEW_MESSAGES,
                    "New messages", //appContext.getString(R.string.channel_new_messages),
                    // The importance must be IMPORTANCE_HIGH to show Bubbles.
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    //description = appContext.getString(R.string.channel_new_messages_description)
                    description = "All new incoming messages."
                },
            )
        }

        if (notificationManager.getNotificationChannel(CHANNEL_CALL) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_CALL,
                    "Call records", //appContext.getString(R.string.channel_new_messages),
                    // The importance must be IMPORTANCE_HIGH to show Bubbles.
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    //description = appContext.getString(R.string.channel_new_messages_description)
                    description = "All new incoming calls."
                },
            )
        }
    }

    private fun User.toShortcut(
        additional: ShortcutInfoCompat.Builder.() -> ShortcutInfoCompat.Builder = { this },
    ): ShortcutInfoCompat {
//        val icon = IconCompat.createWithAdaptiveBitmap(
//            appContext.resources.assets.open(icon).use { input ->
//                BitmapFactory.decodeStream(input)
//            },
//        )
        return ShortcutInfoCompat.Builder(appContext, shortcutId)
            .setLocusId(LocusIdCompat(shortcutId))
            .setActivity(ComponentName(appContext, MainActivity::class.java))
            .setShortLabel(name)
            //.setIcon(icon)
            .setLongLived(true)
            .setCategories(hashSetOf("com.google.android.samples.socialite.category.SHARE_TARGET"))
            .setIntent(
                Intent(appContext, MainActivity::class.java)
                    .setAction(Intent.ACTION_VIEW)
                    .setData(contentUri),
            )
            .setPerson(
                Person.Builder()
                    .setName(name)
                    //.setIcon(icon)
                    .build(),
            )
            .additional()
            .build()
    }

    @WorkerThread
    fun pushShortcut(contact: User, reason: PushReason? = null) {
        ShortcutManagerCompat.pushDynamicShortcut(
            appContext,
            contact.toShortcut {
                when (reason) {
                    PushReason.IncomingMessage -> {
                        addCapabilityBinding("actions.intent.RECEIVE_MESSAGE")
                    }

                    PushReason.OutgoingMessage -> {
                        addCapabilityBinding("actions.intent.SEND_MESSAGE")
                    }

                    else -> this
                }
            },
        )
    }

    private fun flagUpdateCurrent(mutable: Boolean): Int {
        return if (mutable) {
            if (Build.VERSION.SDK_INT >= 31) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        }
    }

    @WorkerThread
    fun showNotification(
        contact: User,
        messages: List<ChatMessage>,
        fromUser: Boolean,
        update: Boolean = false,
    ) {
        val icon = IconCompat.createWithAdaptiveBitmapContentUri(contact.iconUri)
        val user = Person.Builder().setName("You").build()
        val person = Person.Builder().setName(contact.name).setIcon(icon).build()

        val pendingIntent = PendingIntent.getActivity(
            appContext,
            REQUEST_BUBBLE,
            // Launch BubbleActivity as the expanded bubble.
            Intent(appContext, BubbleActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(contact.contentUri),
            flagUpdateCurrent(mutable = true),
        )
        // Let's add some more content to the notification in case it falls back to a normal
        // notification.
        val messagingStyle = NotificationCompat.MessagingStyle(user)
        val firstId = messages.first().id
        for (message in messages.reversed()) {
            val date = message.timestamp?.toDate() ?: Timestamp.now().toDate()
            val m = NotificationCompat.MessagingStyle.Message(
                message.message,
                //message.timestamp,
                date.time,
                //if (message.isIncoming) person else null,
                person,
            ).apply {
                //if (message.mediaUri != null) {
                //    setData(message.mediaMimeType, message.mediaUri.toUri())
                //}
            }
            if (message.id == firstId) {
                messagingStyle.addMessage(m)
            } else {
                messagingStyle.addHistoricMessage(m)
            }
        }

        val lastMessageDate = messages.last().timestamp?.toDate() ?: Timestamp.now().toDate()
        val builder = NotificationCompat.Builder(appContext, CHANNEL_NEW_MESSAGES)
            // A notification can be shown as a bubble by calling setBubbleMetadata()
            .setBubbleMetadata(
                NotificationCompat.BubbleMetadata.Builder(pendingIntent, icon)
                    // The height of the expanded bubble.
                    .setDesiredHeight(
                        appContext.resources.getDimensionPixelSize(R.dimen.bubble_height),
                    )
                    .apply {
                        // When the bubble is explicitly opened by the user, we can show the bubble
                        // automatically in the expanded state. This works only when the app is in
                        // the foreground.
                        if (fromUser) {
                            setAutoExpandBubble(true)
                        }
                        if (fromUser || update) {
                            setSuppressNotification(true)
                        }
                    }
                    .build(),
            )
            // The user can turn off the bubble in system settings. In that case, this notification
            // is shown as a normal notification instead of a bubble. Make sure that this
            // notification works as a normal notification as well.
            .setContentTitle(contact.name)
            .setSmallIcon(R.drawable.ic_message)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setShortcutId(contact.shortcutId)
            // This ID helps the intelligence services of the device to correlate this notification
            // with the corresponding dynamic shortcut.
            .setLocusId(LocusIdCompat(contact.shortcutId))
            .addPerson(person)
            .setShowWhen(true)
            // The content Intent is used when the user clicks on the "Open Content" icon button on
            // the expanded bubble, as well as when the fall-back notification is clicked.
            .setContentIntent(
                PendingIntent.getActivity(
                    appContext,
                    REQUEST_CONTENT,
                    Intent(appContext, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contact.contentUri),
                    flagUpdateCurrent(mutable = false),
                ),
            )
            // Direct Reply
            .addAction(
                NotificationCompat.Action
                    .Builder(
                        IconCompat.createWithResource(appContext, R.drawable.ic_send),
                        //appContext.getString(R.string.label_reply),
                        "Reply",
                        PendingIntent.getBroadcast(
                            appContext,
                            REQUEST_CONTENT,
                            Intent(appContext, ReplyReceiver::class.java)
                                .setData(contact.contentUri),
                            flagUpdateCurrent(mutable = true),
                        ),
                    )
                    .addRemoteInput(
                        RemoteInput.Builder(ReplyReceiver.KEY_TEXT_REPLY)
                            //.setLabel(appContext.getString(R.string.hint_input))
                            .setLabel("Type a message…")
                            .build(),
                    )
                    .setAllowGeneratedReplies(true)
                    .build(),
            )
            // Let's add some more content to the notification in case it falls back to a normal
            // notification.
            .setStyle(messagingStyle)
            //.setWhen(messages.last().timestamp)
            .setWhen(lastMessageDate.time)
        // Don't sound/vibrate if an update to an existing notification.
        if (update) {
            builder.setOnlyAlertOnce(true)
        }
        notificationManager.notify(contact.id.hashCode(), builder.build())
    }

    @WorkerThread
    fun showCallNotification(
        contact: User,
        //roomId: String
//        messages: List<ChatMessage>,
//        fromUser: Boolean,
//        update: Boolean = false,
    ) {
        val icon = IconCompat.createWithAdaptiveBitmapContentUri(contact.iconUri)
        val user = Person.Builder().setName("You").build()
        val person = Person.Builder().setName(contact.name).setIcon(icon).build()

        val pendingIntent = PendingIntent.getActivity(
            appContext,
            REQUEST_CALL,
            // Launch BubbleActivity as the expanded bubble.
            Intent(appContext, WebRtcActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .putExtra("roomId", contact.id)
                .putExtra("receiver", true)
                .setData(contact.contentUri),
            flagUpdateCurrent(mutable = true),
        )

        val builder = NotificationCompat.Builder(appContext, CHANNEL_CALL)

            // The user can turn off the bubble in system settings. In that case, this notification
            // is shown as a normal notification instead of a bubble. Make sure that this
            // notification works as a normal notification as well.
            .setContentTitle(contact.name)
            .setSmallIcon(R.drawable.ic_message)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setShortcutId(contact.shortcutId)
            // This ID helps the intelligence services of the device to correlate this notification
            // with the corresponding dynamic shortcut.
            .setLocusId(LocusIdCompat(contact.shortcutId))
            .addPerson(person)
            .setShowWhen(true)
            // The content Intent is used when the user clicks on the "Open Content" icon button on
            // the expanded bubble, as well as when the fall-back notification is clicked.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setUsesChronometer(false)
            .setColorized(true)
            .setStyle(
                NotificationCompat.CallStyle.forIncomingCall(
                    Person.Builder()
                        .setName("denemee")
                        .build(),
                    pendingIntent,
                    pendingIntent,
                )
            )
            .setFullScreenIntent(pendingIntent, true)

        notificationManager.notify(contact.id.hashCode(), builder.build())
    }

    fun dismissNotification(chatId: Long) {
        notificationManager.cancel(chatId.toInt())
    }

    fun canBubble(contact: User): Boolean {
        if (Build.VERSION.SDK_INT < 30) return false
        val channel = notificationManager.getNotificationChannel(
            CHANNEL_NEW_MESSAGES,
            contact.shortcutId,
        )
        return notificationManager.areBubblesEnabledCompat() || channel?.canBubble() == true
    }

    @RequiresApi(29)
    private fun NotificationManager.areBubblesEnabledCompat(): Boolean {
        return if (Build.VERSION.SDK_INT >= 31) {
            areBubblesEnabled()
        } else {
            @Suppress("DEPRECATION")
            areBubblesAllowed()
        }
    }
}
