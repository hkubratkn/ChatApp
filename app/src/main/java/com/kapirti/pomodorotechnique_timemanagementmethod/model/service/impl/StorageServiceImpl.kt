package com.kapirti.pomodorotechnique_timemanagementmethod.model.service.impl

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.AccountService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.StorageService
import com.kapirti.pomodorotechnique_timemanagementmethod.model.service.trace
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class StorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val storage: FirebaseStorage
): StorageService {
    override suspend fun getPhoto(uid: String): String = photoReference(uid).downloadUrl.await().toString()
    override suspend fun getVideo(uid: String): String = videoReference(uid).downloadUrl.await().toString()

    override suspend fun savePhoto(photo: ByteArray, uid: String): Unit =
        trace(SAVE_USER_PHOTOS) {
            photoReference(uid).putBytes(photo).await()
        }
    override suspend fun saveVideo(uri: Uri, uid: String): Unit = trace(SAVE_VIDEO) {
        videoReference(uid).putFile(uri).await()
    }

    private fun storageReference(): StorageReference = storage.reference.child(PHOTOS_STORAGE).child(auth.currentUserId)
    private fun photoReference(uid: String): StorageReference = storageReference().child("${uid}.jpg")
    private fun videoReference(uid: String): StorageReference = storageReference().child("${uid}.mp4")

    companion object {
        private const val PHOTOS_STORAGE = "Photos"
        private const val SAVE_USER_PHOTOS = "storageUserPhotos"
        private const val SAVE_VIDEO = "storageVideo"
    }
}
