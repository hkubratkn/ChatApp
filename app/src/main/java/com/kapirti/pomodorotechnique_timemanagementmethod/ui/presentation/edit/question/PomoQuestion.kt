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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.QuestionWrapper


@Composable
fun PomoQuestion(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    value: String,
    minusBtnState: Boolean,
    plusBtnState: Boolean,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 40.sp
            )


            Button(enabled = minusBtnState, onClick = { onMinusClick() }) {
                Text(
                    text = "-",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Button(enabled = plusBtnState, onClick = { onPlusClick() }) {
                Text(
                    text = "+",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}
