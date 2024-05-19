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

package com.kapirti.video_food_delivery_shopping.common.ext

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun timeCustomFormat(timestamps: Long): String {
    return SimpleDateFormat(DateFormats.DATE_TIME_FORMAT_CUSTOM_FORMAT.format, Locale.ENGLISH).format(timestamps * 1000)
}

fun getTimeFromTimestamp(timestamp: Long): String {
    return SimpleDateFormat(DateFormats.TIME_AM_PM_FORMAT.format, Locale.ENGLISH).format(timestamp * 1000)
}

fun getYearFromTimeStamp(timestamp: Long): String {
    return SimpleDateFormat("yyyy", Locale.ENGLISH).format(timestamp * 1000)
}

fun getBirthdayYear(birthday: String): String{
    val birthdayList: List<String> = birthday.split(" ").map{ it -> it.trim() }
    return birthdayList.last()
}

fun calculateBirthday(timestamp: Timestamp, birthday: String): String {
    val timestampLong = timestamp.seconds
    val currentYear =  getYearFromTimeStamp(timestampLong)
    val birthdayYear = getBirthdayYear(birthday)
    try {
        return (currentYear.toInt() - birthdayYear.toInt()).toString()
    } catch(e: NumberFormatException){
        return "0"
    }
}


enum class DateFormats(val format: String) {
    DEFAULT_FORMAT("yyyy-MM-dd'T'HH:mm:ss"), //2021-05-20T11:28:24Z


    DEFAULT_FORMAT_WITHOUT_TIME("yyyy-MM-dd"), //2021-05-20
    DATE_TIME_YY_MM_FORMAT("yyyy-MM-dd'T'HH:mm"), //2021-05-20T11:28
    DATE_TIME_YY_MM_FULL_TIME_FORMAT("yyyy-MM-dd'T'HH:mm:ss"), //2021-05-20T11:28
    DATE_MM_DD_YY_FORMAT("MM-dd-yyyy"), //05-20-2021
    DATE_MM_DD_FORMAT("MMM dd, yyyy"), //May 20, 2021
    FULL_DATE_TIME_DD_MM_FORMAT("dd-MM-yyyy'T'HH:mm:ss"), //2021-05-20T11:28:24
    DATE_YY_MM_FORMAT("yyyy-MM-dd"), //2021-05-20
    DATE_MONTH_OF_YEAR_FORMAT("d MMMM, yyyy"), //20 May, 2021
    DAY_OF_WEEK_MONTH_FORMAT("EEE, d MMM"), //Thu, 20 May
    DATE_MONTH_FORMAT("d MMM"), // 20 MAY
    DATE_TIME_FORMAT_CUSTOM_FORMAT("dd.MM.yyyy' - ' HH:mm:ss"), //21.06.2021 - 10:10:18
    TIME_AM_PM_FORMAT("hh:mm a"), // 10:10:18 hh:mm aa
}
