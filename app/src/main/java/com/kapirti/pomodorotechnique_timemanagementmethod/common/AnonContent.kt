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

package com.kapirti.pomodorotechnique_timemanagementmethod.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kapirti.pomodorotechnique_timemanagementmethod.R
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.RegularCardEditor
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.card

@Composable
fun AnonContent (
    navigateLogin: () -> Unit,
    navigateRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegularCardEditor(
            stringResource(R.string.log_in),
            Icons.AutoMirrored.Default.Login, "", Modifier.card()
        ) { navigateLogin() }

        RegularCardEditor(
            stringResource(R.string.register),
            Icons.Default.PersonAdd, "", Modifier.card()
        ) { navigateRegister() }
    }
}
