package com.kapirti.ira.chatext.repository
/**
import com.kapirti.video_food_delivery_shopping.model.service.AccountService
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.soci.di.AppCoroutineScope
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


@Singleton
class ChatRepository @Inject internal constructor(
// private val chatDao: ChatDao,
// private val messageDao: MessageDao,
//  private val contactDao: ContactDao,
private val notificationHelper: NotificationHelper,
private val accountService: AccountService,
private val firestoreService: FirestoreService,
//    private val widgetModelRepository: WidgetModelRepository,
@AppCoroutineScope
private val coroutineScope: CoroutineScope,
) {


init {
notificationHelper.setUpNotificationChannels()
}


suspend fun sendMessage(
chatId: Long,
text: String,
mediaUri: String?,
mediaMimeType: String?,
) {
val senderId = accountService.currentUserId ?: return
firestoreService.setChatMessage(
Message(

)
)


val detail = chatDao.loadDetailById(chatId) ?: return
messageDao.insert(
Message(
id = 0L,
chatId = chatId,
// User
senderId = 0L,
text = text,
mediaUri = mediaUri,
mediaMimeType = mediaMimeType,
timestamp = System.currentTimeMillis(),
),
)
notificationHelper.pushShortcut(detail.firstContact, PushReason.OutgoingMessage)
// Simulate a response from the peer.
// The code here is just for demonstration purpose in this sample.
// Real apps will use their server backend and Firebase Cloud Messaging to deliver messages.
coroutineScope.launch {
// The person is typing...
delay(5000L)
// Receive a reply.
messageDao.insert(
detail.firstContact.reply(text).apply { this.chatId = chatId }.build(),
)
notificationHelper.pushShortcut(detail.firstContact, PushReason.IncomingMessage)
// Show notification if the chat is not on the foreground.
if (chatId != currentChat) {
notificationHelper.showNotification(
detail.firstContact,
messageDao.loadAll(chatId),
false,
)
}
widgetModelRepository.updateUnreadMessagesForContact(contactId = detail.firstContact.id, unread = true)
}
}

suspend fun updateNotification(chatId: Long) {
val detail = chatDao.loadDetailById(chatId) ?: return
val messages = messageDao.loadAll(chatId)
notificationHelper.showNotification(
detail.firstContact,
messages,
fromUser = false,
update = true,
)
}


}

import com.zepi.social_chat_food.model.User
import com.zepi.social_chat_food.model.service.FirestoreService
import com.zepi.social_chat_food.soci.data.ChatDao
import com.zepi.social_chat_food.soci.data.ContactDao
import com.zepi.social_chat_food.soci.data.MessageDao
import com.zepi.social_chat_food.soci.di.AppCoroutineScope
import com.zepi.social_chat_food.soci.model.ChatDetail
import com.zepi.social_chat_food.soci.model.Message
import com.zepi.social_chat_food.soci.widget.model.WidgetModelRepository


private var currentChat: Long = 0L


fun getChats(): Flow<List<ChatDetail>> {
return chatDao.allDetails()
}

fun findUser(userId: String): Flow<User?> {
return firestoreService.getUser(userId)
}

fun findMessages(chatId: String): Flow<List<Message>> {
return firestoreService.getChatMessages(chatId)
}



suspend fun clearMessages() {
messageDao.clearAll()
}



fun activateChat(chatId: Long) {
currentChat = chatId
notificationHelper.dismissNotification(chatId)
coroutineScope.launch {
chatDao.detailById(currentChat).filterNotNull().collect { detail ->
widgetModelRepository.updateUnreadMessagesForContact(detail.firstContact.id, false)
}
}
}

fun deactivateChat(chatId: Long) {
if (currentChat == chatId) {
currentChat = 0
}
}

suspend fun showAsBubble(chatId: Long) {
val detail = chatDao.loadDetailById(chatId) ?: return
val messages = messageDao.loadAll(chatId)
notificationHelper.showNotification(detail.firstContact, messages, true)
}

suspend fun canBubble(chatId: Long): Boolean {
val detail = chatDao.loadDetailById(chatId) ?: return false
return notificationHelper.canBubble(detail.firstContact)
}
}
 */
