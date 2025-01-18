/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.test.test.model

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

private const val SHORTCUT_PREFIX = "contact_"

data class User(
    @DocumentId val id: String = "id",
    val name: String = "name",
    val surname: String = "surname",
    val photo: String = "Photo",
    val description: String = "Description",
    val online: Boolean = false,
    @ServerTimestamp
    var lastSeen: Timestamp? = null,
    @ServerTimestamp
    var dateOfCreation: Timestamp? = null,
    var fcmToken: String = "",
    var status: String = "",
    var typingTo: String = ""
) {
    val shortcutId: String
        get() = "$SHORTCUT_PREFIX$id"

    val contentUri: Uri
        get() = "https://socialite.google.com/chat/$id".toUri()

    val iconUri: Uri
        get() = "".toUri()
}

fun extractChatId(shortcutId: String): String {
    if (!shortcutId.startsWith(SHORTCUT_PREFIX)) return ""
    return try {
        shortcutId.substring(SHORTCUT_PREFIX.length)
    } catch (e: NumberFormatException) {
        ""
    }
}

