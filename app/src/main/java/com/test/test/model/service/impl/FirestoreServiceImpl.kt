package com.test.test.model.service.impl

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import com.test.test.model.User
import com.test.test.model.service.AccountService
import com.test.test.model.service.FirestoreService
import kotlinx.coroutines.flow.map

class FirestoreServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService,
): FirestoreService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<User>>
        get() = auth.currentUser.flatMapLatest { user ->
            userCollection().orderBy(DATE_OF_CREATION_FIELD, Query.Direction.DESCENDING).snapshots().map { snapshot -> snapshot.toObjects() } }


    private fun userCollection(): CollectionReference = firestore.collection(USER_COLLECTION)


    companion object {
        private const val USER_COLLECTION = "User"
        private const val DATE_OF_CREATION_FIELD = "dateOfCreation"
    }
}
