package com.kapirti.ira.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.ira.common.composable.BasicField
import com.kapirti.ira.common.ext.fieldModifier
import com.kapirti.ira.ui.presentation.edit.QuestionWrapper

@Composable
fun FieldQuestion(
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
        BasicField(text, value, onValueChange, fieldModifier)
    }
}