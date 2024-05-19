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

package com.kapirti.video_food_delivery_shopping.ui.presentation.chatnope
/**
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.kapirti.video_food_delivery_shopping.model.Chat
import com.kapirti.video_food_delivery_shopping.model.User
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ChatViewModelNope @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    logService: LogService,
): ZepiViewModel(logService) {
    val uid = accountService.currentUserId
    val date = Timestamp.now()

    private val _text = mutableStateOf<String?>(null)
    val text: String?
        get() = _text.value

    private val _me = MutableStateFlow<User>(User())
    var me: StateFlow<User> = _me

    private val _user = MutableStateFlow<User>(User())
    var user: StateFlow<User> = _user

    fun onTextChange(newValue: String) {
        _text.value = newValue
    }


    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUser(uid)?.let { itUser ->
                _me.value = itUser
                firestoreService.getUser(userUid)?.let { itPartner ->
                    _user.value = itPartner
                }
            }
        }
    }


    fun onSendClick(
        chatId: String, who: String, openAndPopUpChatNopeToExist: () -> Unit,
        partnerName: String, partnerSurname: String, partnerPhoto: String, partnerUid: String,
        profileName: String, profileSurname: String, profilePhoto: String
    ){
        launchCatching {
/**            firestoreService.saveChatRow(chatId = chatId,
                ChatMessage(
                    text = _text.value ?: "hello",
                    who = who,
                    timestamp = date,
                    isIncoming = null,
                )*/

            firestoreService.saveUserChat(uid = uid, chatId = chatId,
                Chat(
                    chatId = chatId,
                    partnerName = partnerName,
                    partnerSurname = partnerSurname,
                    partnerPhoto = partnerPhoto,
                    partnerUid = partnerUid,
                    date = date
                )
            )
            firestoreService.saveUserChat(uid = partnerUid, chatId = chatId,
                Chat(
                    chatId = chatId,
                    partnerName = profileName,
                    partnerSurname = profileSurname,
                    partnerPhoto = profilePhoto,
                    partnerUid = uid,
                    date = date
                )
            )
            openAndPopUpChatNopeToExist()
        }
    }
}
*/
