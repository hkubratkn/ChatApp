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

package com.kapirti.ira.model.service.impl

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kapirti.ira.model.service.AccountService
import com.kapirti.ira.model.service.StorageService
import com.kapirti.ira.model.service.trace
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class StorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val storage: FirebaseStorage
): StorageService {
    override suspend fun getPhoto(uid: String): String = storageReference(uid).downloadUrl.await().toString()
    override suspend fun savePhoto(photo: ByteArray, uid: String): Unit = trace(SAVE_USER_PHOTOS){ storageReference(uid).putBytes(photo).await() }

    private fun storageReference(uid: String): StorageReference = storage.reference.child(PHOTOS_STORAGE).child(auth.currentUserId).child("${uid}.jpg")

    companion object {
        private const val PHOTOS_STORAGE = "Photos"
        private const val SAVE_USER_PHOTOS = "storageUserPhotos"
    }

   /** override suspend fun getProfile(): String =
    override suspend fun saveBytes(photo: ByteArray): Unit = trace(SAVE_PHOTO_TRACE) {



        private const val SAVE_PHOTO_TRACE = "savePhoto"
    }*/
}
