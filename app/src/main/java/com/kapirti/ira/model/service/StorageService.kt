package com.kapirti.ira.model.service

interface StorageService {
    suspend fun getPhoto(uid: String): String
    suspend fun savePhoto(photo: ByteArray, uid: String)
   // suspend fun getProfile(): String
   // suspend fun saveBytes(photo: ByteArray)
}
