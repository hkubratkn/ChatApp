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

package com.test.test.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DangerousCardEditor(
    title: String,
    icon: ImageVector,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.primary, modifier)
}


@Composable
fun RegularCardEditor(
    title: String,
    icon: ImageVector,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.onSurface, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditor(
    title: String,
    icon: ImageVector,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    color = highlightColor
                )
            }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = highlightColor
            )
        }
    }
}

@Composable
fun ThemeCardEditor(
    @StringRes title: Int,
    content: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    highlightColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier
) {
    Card(
        modifier = modifier.background(MaterialTheme.colorScheme.onPrimary),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun PomoCardEditor(
    @StringRes title: Int,
    content: String,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    plusBtnState: Boolean,
    minusBtnState: Boolean,
    highlightColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Button(enabled = minusBtnState, onClick = { onMinusClick() }) { Text(text = "-", color = highlightColor) }
            Button(enabled = plusBtnState, onClick = { onPlusClick() }) { Text(text = "+", color = highlightColor) }
        }
    }
}

@Composable
fun BottomCard(
    @StringRes start: Int,
    @StringRes finish: Int,
    startBtnStatus: Boolean,
    finishBtnStatus: Boolean,
    onStartClick: () -> Unit,
    onFinishClick: () -> Unit,
) {
    BottomCardEditor(
        start = start,
        finish = finish,
        startBtnStatus = startBtnStatus,
        finishBtnStatus = finishBtnStatus,
        onStartClick = { onStartClick() },
        onFinishClick = { onFinishClick() },
    )

}

@Composable
private fun BottomCardEditor(
    @StringRes start: Int,
    @StringRes finish: Int,
    startBtnStatus: Boolean,
    finishBtnStatus: Boolean,
    onStartClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        FinishButtonComposable(
            title = finish,
            btnState = finishBtnStatus,
            backgroundColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(0.3f),
            onButtonClick = { onFinishClick() }
        )
        Spacer(modifier = Modifier.width(10.dp))
        StartButtonComposable(
            title = start,
            btnState = startBtnStatus,
            modifier = Modifier.fillMaxWidth(0.4f),
            onButtonClick = { onStartClick() }
        )
    }
}
