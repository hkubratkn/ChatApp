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

package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.job

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.spacer
import com.kapirti.pomodorotechnique_timemanagementmethod.model.Job

@Composable
fun JobItem(
    job: Job,
    onItemClick: (Job) -> Unit
) {
    Card(Modifier.fillMaxWidth().padding(8.dp, 16.dp)){
        Column(Modifier.fillMaxWidth().clickable { onItemClick(job) }){
            Text(job.title)
            Spacer(Modifier.spacer())
            Text(job.description)
        }
    }
}
