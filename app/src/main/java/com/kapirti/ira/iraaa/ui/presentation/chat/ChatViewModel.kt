package com.kapirti.ira.iraaa.ui.presentation.chat
/**
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.google.firebase.Timestamp
import com.zepi.social_chat_food.iraaa.core.room.profile.Profile
import com.zepi.social_chat_food.iraaa.core.room.profile.ProfileDao
import com.zepi.social_chat_food.model.Block
import com.zepi.social_chat_food.model.ChatRow
import com.zepi.social_chat_food.model.Report
import com.zepi.social_chat_food.model.User
import com.zepi.social_chat_food.model.service.AccountService
import com.zepi.social_chat_food.model.service.ConfigurationService
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.model.service.LogService
import com.zepi.social_chat_food.ui.presentation.QChatViewModel

@HiltViewModel
class ChatViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val configurationService: ConfigurationService,
    private val profileDao: ProfileDao,
): QChatViewModel(logService) {
    var showBlockDialog = mutableStateOf(false)
    var showReportDialog = mutableStateOf(false)
    val options = mutableStateOf<List<String>>(listOf())
    val uid = accountService.currentUserId
    val profile: Flow<Profile> = profileDao.getProfile()

    private val _chatRow = MutableStateFlow<List<ChatRow>>(emptyList())
    var chatRow: StateFlow<List<com.zepi.social_chat_food.model.ChatRow>> = _chatRow

    private val _user = MutableStateFlow<User>(com.zepi.social_chat_food.model.User())
    var user: StateFlow<com.zepi.social_chat_food.model.User> = _user

    private val _unread = MutableStateFlow<Int>(0)
    var unread: StateFlow<Int> = _unread

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: Boolean
        get() = _isLoading.value


    fun initialize(chatId: String, uid: String) {
        launchCatching {
            firestoreService.chatRow(chatId).collect { item ->
                _chatRow.value = item
                firestoreService.getUser(uid)?.let { itUser ->
                    _user.value = itUser
                   // firestoreService.updateUserChatUnreadZero(chatId = chatId)
                    firestoreService.getUserChat(uid = uid, chatId = chatId)?.let { unr ->
                        _unread.value = unr.unread
                    }
                }
            }
        }
    }

   // private var job: Job? = null

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun onDoneClick(
        chatId: String,
        text: String,
        token: String,
        userNameSurname: String,
        partnerUid: String,
        unread: Int
    ) {
        val date = Timestamp.now()
        if (text == "") {
            return
        }

        launchCatching {
            firestoreService.saveChatRow(
                chatId = chatId,
                com.zepi.social_chat_food.model.ChatRow(text = text, who = uid, date = date)
            )
/**            firestoreService.updateUserChatUnreadIncrease(
                uid = partnerUid,
                chatId = chatId,
                unread = unread + 1
            )

            job?.cancel()
            job = viewModelScope.launch {
                try {
                    val response = repository.postNotification(
                        com.kapirti.ira.oldproject.model.NotificationModel(
                            data = com.kapirti.ira.oldproject.model.NotificationDataModel(
                                title = userNameSurname,
                                message = text
                            ),
                            to = token
                            /**token.ifBlank { Notification.TOPIC }*/
                            /**token.ifBlank { Notification.TOPIC }*/
                        )
                    )
                } catch (e: HttpException) {
                    SnackbarManager.showMessageString(e.localizedMessage ?: "")
                }
            }*/
        }
    }

    fun onChatActionClick(action: String) {
        when (ChatActionOption.getByTitle(action)) {
            ChatActionOption.Block -> onBlockClick()
            ChatActionOption.Report -> onReportClick()
        }
    }

    private fun onBlockClick() {
        showBlockDialog.value = true
    }

    private fun onReportClick() {
        showReportDialog.value = true
    }

    fun onBlockButtonClick(
        popUpScreen: () -> Unit, chatId: String, name: String, surname: String, photo: String,
        partnerUid: String, partnerName: String, partnerSurname: String, partnerPhoto: String
    ) {
        val date = Timestamp.now().toDate()
        launchCatching {
            firestoreService.block(
                uid = uid,
                partnerUid = partnerUid,
                block = com.zepi.social_chat_food.model.Block(
                    uid = partnerUid,
                    name = partnerName,
                    surname = partnerSurname,
                    photo = partnerPhoto,
                    date = date
                )
            )
            firestoreService.block(
                uid = partnerUid,
                partnerUid = uid,
                block = com.zepi.social_chat_food.model.Block(
                    uid = uid,
                    name = name,
                    surname = surname,
                    photo = photo,
                    date = date
                )
            )
            firestoreService.deleteChat(chatId = chatId)
            firestoreService.deleteUserChat(uid = uid, chatId = chatId)
            firestoreService.deleteUserChat(uid = partnerUid, chatId = chatId)
            popUpScreen()
        }
    }

    fun onReportButtonClick(
        popUpScreen: () -> Unit, chatId: String, name: String, surname: String, photo: String,
        partnerUid: String, partnerName: String, partnerSurname: String, partnerPhoto: String
    ) {
        val date = Timestamp.now()
        launchCatching {
            firestoreService.report(
                uid = uid,
                partnerUid = partnerUid,
                report = com.zepi.social_chat_food.model.Report(
                    uid = uid,
                    name = name,
                    surname = surname,
                    photo = photo,
                    date = date.toDate()
                )
            )
            onBlockButtonClick(
                popUpScreen = popUpScreen,
                chatId = chatId,
                name = name,
                surname = surname,
                photo = photo,
                partnerUid = partnerUid,
                partnerName = partnerName,
                partnerSurname = partnerSurname,
                partnerPhoto = partnerPhoto
            )
        }
    }

    fun refresh() {
        _isLoading.value = true
        launchCatching {
            _isLoading.value = false
        }
    }

    fun loadChatOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = ChatActionOption.getOptions(hasEditOption)
    }
}
*/
