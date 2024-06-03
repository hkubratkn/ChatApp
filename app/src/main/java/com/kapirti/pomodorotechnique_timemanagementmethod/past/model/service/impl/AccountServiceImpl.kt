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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.impl
/**
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserUid
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) :
    com.kapirti.pomodorotechnique_timemanagementmethod.past.model.service.AccountService {
    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val currentUserEmail: String
        get() = auth.currentUser?.email.orEmpty()

    override val currentUserDisplayName: String
        get() = auth.currentUser?.displayName.orEmpty()


    override val currentUser: Flow<com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserUid>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let {
                        com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserUid(
                            it.uid
                        )
                    } ?: com.kapirti.pomodorotechnique_timemanagementmethod.past.model.UserUid())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }


    override suspend fun displayName(newValue: String){
        val profileUpdates = userProfileChangeRequest {
            displayName = newValue
        }
        auth.currentUser?.let { it.updateProfile(profileUpdates).await() }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
}
*/
