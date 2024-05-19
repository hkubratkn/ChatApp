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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.video_food_delivery_shopping.iraaa.core.viewmodel.IncludeChatViewModel
import com.kapirti.video_food_delivery_shopping.iraaa.core.viewmodel.IncludeUserUidViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatNopeScreen(
    popUp: () -> Unit,
    openAndPopUpChatNopeToExist: () -> Unit,
    includeUserUidViewModel: IncludeUserUidViewModel,
    includeChatViewModel: IncludeChatViewModel,
    showInterstialAd: () -> Unit,
    modifier: Modifier = Modifier,
  //  viewModel: ChatViewModelNope = hiltViewModel()
) {}
  /**  LaunchedEffect(Unit) {
        viewModel.initialize(
            userUid = includeUserUidViewModel.userUid ?: "",
        )
    }


    val user = viewModel.user.collectAsStateWithLifecycle()
    val me = viewModel.me.collectAsStateWithLifecycle()

    val userName = user.value.name
    val userSurname = user.value.surname
    val userPhoto = user.value.photo
    val userUid = user.value.uid

    val profileName = me.value.name
    val profileSurname = me.value.surname
    val profilePhoto = me.value.photo

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
                            Chat(
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
