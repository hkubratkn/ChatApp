package com.kapirti.video_food_delivery_shopping.model

import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_1
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_10
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_11
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_12
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_13
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_2
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_3
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_4
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_5
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_6
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_7
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_8
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_9
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_14
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_15
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_16
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_17
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_18
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_19
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_20
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_21
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_22
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_23
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_24
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_25
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_26
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_27
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_28
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_29
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_30
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_31
import com.kapirti.video_food_delivery_shopping.core.constants.Avatar.AVATAR_32
import com.kapirti.video_food_delivery_shopping.core.constants.ConsGender.FEMALE
import com.kapirti.video_food_delivery_shopping.core.constants.ConsGender.MALE
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.DANCE
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.READ
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.PLAY_GAMES
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.DRAW
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.WATCH_MOVIES
import com.kapirti.video_food_delivery_shopping.core.constants.Hobby.WORK_OUT
import com.kapirti.video_food_delivery_shopping.core.constants.LangConst.LANG_TURKISH
import com.kapirti.video_food_delivery_shopping.core.constants.LangConst.LANG_ENGLISH
import com.kapirti.video_food_delivery_shopping.core.constants.LangConst.LANG_PERSIAN

object QChatRepo {
    fun getGender(): List<String> = genders
    fun getAvatar(): List<String> = avatars
    fun getHobbies(): List<String> = hobbies
    fun getLangs() : List<String> = langs
}

private val genders = listOf(MALE, FEMALE)
private val avatars = listOf(AVATAR_1,AVATAR_2,AVATAR_3,AVATAR_4,AVATAR_5,AVATAR_6,AVATAR_7,AVATAR_8,AVATAR_9,AVATAR_10,AVATAR_11,AVATAR_12,AVATAR_13,AVATAR_14,AVATAR_15,AVATAR_16,AVATAR_17,AVATAR_18,AVATAR_19,AVATAR_20,AVATAR_21,AVATAR_22,AVATAR_23,AVATAR_24,AVATAR_25,AVATAR_26,AVATAR_27,AVATAR_28,AVATAR_29,AVATAR_30,AVATAR_31,AVATAR_32,)
private val hobbies = listOf(READ, WORK_OUT, DRAW, PLAY_GAMES, DANCE, WATCH_MOVIES)
private val langs = listOf(LANG_ENGLISH, LANG_TURKISH, LANG_PERSIAN)