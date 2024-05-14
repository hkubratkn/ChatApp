package com.zepi.social_chat_food.model.service

import com.zepi.social_chat_food.model.UserUid
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val hasUser: Boolean
    val currentUserId: String
    val currentUserEmail: String
    val currentUserDisplayName: String

    val currentUser: Flow<UserUid>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun displayName(newValue: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}
