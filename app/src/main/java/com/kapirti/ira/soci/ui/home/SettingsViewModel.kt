package com.kapirti.ira.soci.ui.home
/**
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zepi.social_chat_food.soci.data.DatabaseManager
import com.zepi.social_chat_food.soci.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val application: Context,
    private val repository: ChatRepository,
    private val databaseManager: DatabaseManager,
) : ViewModel() {

    fun clearMessages() {
        viewModelScope.launch {
            repository.clearMessages()
            withContext(Dispatchers.IO) {
                databaseManager.wipeAndReinitializeDatabase()
            }
            Toast.makeText(
                application.applicationContext,
                "Messages have been reset",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
*/
