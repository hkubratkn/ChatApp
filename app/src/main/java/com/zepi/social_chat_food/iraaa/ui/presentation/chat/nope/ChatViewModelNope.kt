package com.zepi.social_chat_food.iraaa.ui.presentation.chat.nope
/**
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.zepi.social_chat_food.iraaa.core.room.profile.Profile
import com.zepi.social_chat_food.iraaa.core.room.profile.ProfileDao
import com.zepi.social_chat_food.model.Chat
import com.zepi.social_chat_food.model.ChatRow
import com.zepi.social_chat_food.model.User
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ChatViewModelNope @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val profileDao: ProfileDao,
    logService: LogService,
): QChatViewModel(logService) {
    val uid = accountService.currentUserId
    val date = Timestamp.now()
    val profile: Flow<Profile> = profileDao.getProfile()

    private val _text = mutableStateOf<String?>(null)
    val text: String?
        get() = _text.value

    private val _user = MutableStateFlow<com.zepi.social_chat_food.model.User>(com.zepi.social_chat_food.model.User())
    var user: StateFlow<com.zepi.social_chat_food.model.User> = _user

    fun onTextChange(newValue: String) {
        _text.value = newValue
    }


    fun initialize(userUid: String) {
        launchCatching {
            firestoreService.getUser(uid)?.let { itUser ->
                _user.value = itUser
            }
        }
    }


    fun onSendClick(
        chatId: String, who: String, openAndPopUpChatNopeToExist: () -> Unit,
        partnerName: String, partnerSurname: String, partnerPhoto: String, partnerUid: String,
        profileName: String, profileSurname: String, profilePhoto: String
    ){
        launchCatching {
            firestoreService.saveChatRow(chatId = chatId,
                com.zepi.social_chat_food.model.ChatRow(
                    text = _text.value ?: "hello",
                    who = who,
                    date = date
                )
            )
            firestoreService.saveUserChat(uid = uid, chatId = chatId,
                com.zepi.social_chat_food.model.Chat(
                    chatId = chatId,
                    partnerName = partnerName,
                    partnerSurname = partnerSurname,
                    partnerPhoto = partnerPhoto,
                    partnerUid = partnerUid,
                    date = date
                )
            )
            firestoreService.saveUserChat(uid = partnerUid, chatId = chatId,
                com.zepi.social_chat_food.model.Chat(
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
