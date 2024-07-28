package com.kapirti.pomodorotechnique_timemanagementmethod.model.service

import com.kapirti.pomodorotechnique_timemanagementmethod.model.UserIsAnonymous
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val hasUser: Boolean
    val currentUserId: String
    val currentUserEmail: String
    val currentUserDisplayName: String

    val currentUser: Flow<UserIsAnonymous>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun displayName(newValue: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}
