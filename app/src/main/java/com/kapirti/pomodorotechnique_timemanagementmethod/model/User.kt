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

package com.kapirti.pomodorotechnique_timemanagementmethod.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class User(
    val uid: String = "uid",
    val displayName: String = "Display name",
    val name: String = "Name",
    val surname: String = "Surname",
    val photo: String = "Photo",
    val birthday: String = "Birthday",
    val gender: String = "Gender",
    val hobby: List<String> = listOf(),
    val description: String = "Description",
    val language: String = "Language",
    val online: Boolean = false,
    val token: String = "Token",
    @ServerTimestamp
    var lastSeen: Timestamp? = null,
    @ServerTimestamp
    var date: Timestamp? = null
)