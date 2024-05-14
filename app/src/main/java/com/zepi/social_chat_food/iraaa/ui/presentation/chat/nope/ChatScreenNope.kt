package com.zepi.social_chat_food.iraaa.ui.presentation.chat.nope
/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.Timestamp
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.core.constants.ConsAds.ADS_CHAT_BANNER_ID
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeChatViewModel
import com.zepi.social_chat_food.iraaa.core.viewmodel.IncludeUserUidViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenNope(
    popUp: () -> Unit,
    openAndPopUpChatNopeToExist: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    includeChatViewModel: IncludeChatViewModel,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModelNope = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(
            userUid = includeUserUidViewModel.userUid ?: "",
        )
    }

    val user = viewModel.user.collectAsStateWithLifecycle()
    val profile = viewModel.profile.collectAsStateWithLifecycle(initialValue = null)

    val userName = user.value.name
    val userSurname = user.value.surname
    val userPhoto = user.value.photo
    val userUid = user.value.uid

    val profileName = profile.value?.let { it.namedb } ?: ""
    val profileSurname = profile.value?.let { it.surnamedb } ?: ""
    val profilePhoto = profile.value?.let { it.photodb } ?: ""

    val chatId = "${viewModel.uid}${user.value.uid}"

    Scaffold (
        topBar = {
            Column{
                AdsBannerToolbar(ads = ADS_CHAT_BANNER_ID)
                UserTopBar(title = user.value.name, online = user.value.online, startAction = { popUp() })
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                TextField(
                    value = viewModel.text ?: "",
                    onValueChange = viewModel::onTextChange,
                    placeholder = { Text("Say Hello") },
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        showInterstialAd()
                        includeChatViewModel.addChat(
                            com.zepi.social_chat_food.model.Chat(
                                chatId = chatId,
                                partnerName = userName,
                                partnerSurname = userSurname,
                                partnerPhoto = userPhoto,
                                partnerUid = userUid,
                                date = Timestamp.now()
                            )
                        )
                        viewModel.onSendClick(
                            who = viewModel.uid,
                            chatId = chatId,
                            partnerName = userName,
                            partnerSurname = userSurname,
                            partnerPhoto = userPhoto,
                            partnerUid = userUid,
                            profileName = profileName,
                            profileSurname = profileSurname,
                            profilePhoto = profilePhoto,
                            openAndPopUpChatNopeToExist = openAndPopUpChatNopeToExist
                        )
                    }
                ) {
                    Icon(Icons.Default.Send, null, tint = Color.Green)
                }
            }
        }
    ){ innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.BackHand, null, tint = Color.Yellow, modifier = Modifier.size(50.dp))
            Spacer(Modifier.height(20.dp))
            Text("start conversation")
        }
    }
}
*/
