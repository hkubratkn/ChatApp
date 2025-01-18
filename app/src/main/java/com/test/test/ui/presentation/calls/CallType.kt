/*
 * Copyright (C) 2025 The Android Open Source Project
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

package com.test.test.ui.presentation.calls

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.test.test.R
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


data class RegisteredCall(
    //val id: Int,
    val source: String,
    val callType: CallType?,
    val duration: Duration
)

enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED
}

@Composable
fun CallType.Icon() {
    val icon = when(this) {
        CallType.INCOMING -> Icons.AutoMirrored.Filled.CallReceived
        CallType.OUTGOING -> Icons.AutoMirrored.Filled.CallMade
        CallType.MISSED -> Icons.AutoMirrored.Filled.CallMissed
    }

    androidx.compose.material3.Icon(
        imageVector = icon,
        contentDescription = stringResource(id = R.string.cd_menu),
        //contentDescription = stringResource(id = R.string.cd_menu),
        tint = MaterialTheme.colorScheme.primary
    )

}

//fun humanReadableDuration(s: String): String = Duration.parse(s).toString()
//    .substring(2).toLowerCase().replace(Regex("[hms](?!\$)")) { "${it.value} " }

//fun mockCall(): RegisteredCall {
//    return RegisteredCall(
//        "the caller",
//        CallType.entries[abs(id) % CallType.entries.size],
//        Random(5000).nextInt(0..1000).seconds
//    )
//}
//
//fun dummyCalls(size: Int): List<RegisteredCall> {
//    return (0 until size).map { mockCall() }
//}
