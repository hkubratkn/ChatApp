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

package com.zepi.social_chat_food.model

import com.zepi.social_chat_food.core.constants.ConsGender.FEMALE
import com.zepi.social_chat_food.core.constants.ConsGender.MALE
import com.zepi.social_chat_food.core.constants.Hobby.DANCE
import com.zepi.social_chat_food.core.constants.Hobby.READ
import com.zepi.social_chat_food.core.constants.Hobby.PLAY_GAMES
import com.zepi.social_chat_food.core.constants.Hobby.DRAW
import com.zepi.social_chat_food.core.constants.Hobby.WATCH_MOVIES
import com.zepi.social_chat_food.core.constants.Hobby.WORK_OUT
import com.zepi.social_chat_food.core.constants.LangConst.LANG_TURKISH
import com.zepi.social_chat_food.core.constants.LangConst.LANG_ENGLISH
import com.zepi.social_chat_food.core.constants.LangConst.LANG_PERSIAN

object QChatRepo {
    fun getGender(): List<String> = com.zepi.social_chat_food.model.genders
    fun getAvatar(): List<String> = emptyList()//avatars
    fun getHobbies(): List<String> = com.zepi.social_chat_food.model.hobbies
    fun getLangs() : List<String> = com.zepi.social_chat_food.model.langs
}

private val genders = listOf(MALE, FEMALE)
//private val avatars = listOf(AVATAR_1,AVATAR_2,AVATAR_3,AVATAR_4,AVATAR_5,AVATAR_6,AVATAR_7,AVATAR_8,AVATAR_9,AVATAR_10,AVATAR_11,AVATAR_12,AVATAR_13,AVATAR_14,AVATAR_15,AVATAR_16,AVATAR_17,AVATAR_18,AVATAR_19,AVATAR_20,AVATAR_21,AVATAR_22,AVATAR_23,AVATAR_24,AVATAR_25,AVATAR_26,AVATAR_27,AVATAR_28,AVATAR_29,AVATAR_30,AVATAR_31,AVATAR_32,)
private val hobbies = listOf(READ, WORK_OUT, DRAW, PLAY_GAMES, DANCE, WATCH_MOVIES)
private val langs = listOf(LANG_ENGLISH, LANG_TURKISH, LANG_PERSIAN)
