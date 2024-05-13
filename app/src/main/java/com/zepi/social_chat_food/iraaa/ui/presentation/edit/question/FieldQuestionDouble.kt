package com.zepi.social_chat_food.iraaa.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.iraaa.common.composable.BasicField
import com.zepi.social_chat_food.iraaa.common.ext.fieldModifier
import com.zepi.social_chat_food.iraaa.ui.presentation.edit.QuestionWrapper

@Composable
fun FieldQuestionDouble(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes textFirst: Int,
    @StringRes textSecond: Int,
    valueFirst: String,
    valueSecond: String,
    onFirstChange: (String) -> Unit,
    onSecondChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {
        val fieldModifier = Modifier.fieldModifier()
        BasicField(textFirst, valueFirst, onFirstChange, fieldModifier)
        BasicField(textSecond, valueSecond, onSecondChange, fieldModifier)
    }
}
