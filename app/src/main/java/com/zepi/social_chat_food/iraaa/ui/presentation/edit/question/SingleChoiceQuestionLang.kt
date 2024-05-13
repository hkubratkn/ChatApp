package com.zepi.social_chat_food.iraaa.ui.presentation.edit.question

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.iraaa.common.composable.StaggeredVerticalGrid

@Composable
fun SingleChoiceQuestionLang(
    possibleAnswers: List<String>,
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .selectableGroup()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            possibleAnswers.forEach { course ->
                val selected = course == selectedAnswer
                RadioButtonLang(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = course,
                    imageResourceId = Icons.Default.Flag,
                    selected = selected,
                    onOptionSelected = { onOptionSelected(course) }
                )
            }
        }
    }
}

@Composable
private fun RadioButtonLang(
    text: String,
    imageResourceId: ImageVector,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            ),
        color = if (selected) { MaterialTheme.colorScheme.primaryContainer } else { MaterialTheme.colorScheme.surface },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Icon(imageVector = imageResourceId,
                contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp))
            Text(text)
        }
    }
}
