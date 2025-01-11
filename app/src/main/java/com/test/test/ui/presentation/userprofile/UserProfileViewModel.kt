
package com.test.test.ui.presentation.userprofile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.test.test.model.ChatRoom
import com.test.test.model.service.FirestoreService
import com.test.test.model.service.LogService
import com.test.test.model.service.impl.FirestoreServiceImpl
import com.test.test.ui.presentation.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val firebaseAuth: FirebaseAuth,
    logService: LogService,
): AppViewModel(logService) {

    var uiState = mutableStateOf(UserProfileUiState())
        private set

    fun getUser(userId: String) = viewModelScope.launch {
        val x = firestoreService.getUser(userId)
        uiState.value = uiState.value.copy(user = x)
    }

    fun clearRoomId() {
        uiState.value = uiState.value.copy(navigateRoomId = null)
    }

    fun generatedRoomId() = viewModelScope.launch {

       // val chatRoom = firestoreService.getChatRoom(chatId)

        val myId = firebaseAuth.currentUser?.uid
        val otherId = uiState.value.user?.id

        android.util.Log.d("myTag","trying to get room id ")
        val roomId = FirestoreServiceImpl.getChatRoomId(myId!!, otherId!!)
        android.util.Log.d("myTag","room id from server : $roomId ")

        val chatRoom = firestoreService.getChatRoom(roomId)
        if (chatRoom == null) {
            android.util.Log.d("myTag","no room, so try to create one ")
            val newChatRoom = ChatRoom(
                roomId,
                arrayListOf(myId, otherId),
                Timestamp.now(),
                "",
            )
            val result = firestoreService.setChatRoom(roomId, newChatRoom)
            android.util.Log.d("myTag","setting chat room")
            if (result) {
                android.util.Log.d("myTag","return run blocking with room id :$roomId")
                //return@runBlocking roomId
                uiState.value = uiState.value.copy(navigateRoomId = roomId)
            } else {
                android.util.Log.d("myTag","return run blocking with null")
                //return@runBlocking null
            }
            //uiState.value = uiState.value.copy(chatRoom = newChatRoom)

        } else {
            android.util.Log.d("myTag","already exists, so return immediately :$roomId")
            uiState.value = uiState.value.copy(navigateRoomId = roomId)
            //return@runBlocking roomId
            //uiState.value = uiState.value.copy(chatRoom = chatRoom)
        }


        //val chatRoom = firestoreService.getChatRoom(roomId)
        //uiState.value = uiState.value.copy(chatRoom = chatRoom)
//        if (chatRoom == null) {
//            val newChatRoom = ChatRoom(
//                roomId,
//                arrayListOf(firstUserId, secondUserId),
//                Timestamp.now(),
//                ""
//            )
//            firestoreService.setChatRoom(chatId, newChatRoom)
//            uiState.value = uiState.value.copy(chatRoom = newChatRoom)
//
//        } else {
//            uiState.value = uiState.value.copy(chatRoom = chatRoom)
//        }

    }
}
