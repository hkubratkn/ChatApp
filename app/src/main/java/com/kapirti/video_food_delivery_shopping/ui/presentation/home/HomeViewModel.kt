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

package com.kapirti.video_food_delivery_shopping.ui.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.kapirti.video_food_delivery_shopping.model.service.FirestoreService
import com.kapirti.video_food_delivery_shopping.model.service.LogService
import com.kapirti.video_food_delivery_shopping.soci.ui.stateInUi
import com.kapirti.video_food_delivery_shopping.ui.presentation.ZepiViewModel

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val firestoreService: FirestoreService,
): ZepiViewModel(logService) {

    val users = firestoreService.users
        .stateInUi(emptyList())
}
