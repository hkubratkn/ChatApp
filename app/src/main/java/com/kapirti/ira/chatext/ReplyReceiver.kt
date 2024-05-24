package com.kapirti.ira.chatext
/**
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kapirti.video_food_delivery_shopping.chatext.repository.ChatRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Handles the "Reply" action in the chat notification.
 */
@AndroidEntryPoint
class ReplyReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: ChatRepository
    companion object {
        const val KEY_TEXT_REPLY = "reply"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val results = RemoteInput.getResultsFromIntent(intent) ?: return
        // The message typed in the notification reply.
        val input = results.getCharSequence(KEY_TEXT_REPLY)?.toString()
        val uri = intent.data ?: return
        val chatId = uri.lastPathSegment?.toLong() ?: return

        if (chatId > 0 && !input.isNullOrBlank()) {
            val pendingResult = goAsync()
            val job = Job()
            CoroutineScope(job).launch {
                try {
                    repository.sendMessage(chatId, input.toString(), null, null)
                    // We should update the notification so that the user can see that the reply has
                    // been sent.
                    repository.updateNotification(chatId)
                } finally {
                    pendingResult.finish()
                }
            }
        }
    }
}
*/
