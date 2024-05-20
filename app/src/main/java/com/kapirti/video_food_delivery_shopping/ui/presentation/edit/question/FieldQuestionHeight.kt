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

package com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.video_food_delivery_shopping.common.composable.DescriptionField
import com.kapirti.video_food_delivery_shopping.common.ext.fieldModifier
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.QuestionWrapper

@Composable
fun FieldQuestionHeight(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes text: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {
        val fieldModifier = Modifier.fieldModifier()
        DescriptionField(text, value, onValueChange, fieldModifier)
    }
}