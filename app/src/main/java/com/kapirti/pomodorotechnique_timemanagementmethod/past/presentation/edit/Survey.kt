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

package com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit
/**
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import com.kapirti.pomodorotechnique_timemanagementmethod.past.model.QChatRepo
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.FieldQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.FieldQuestionDouble
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.FieldQuestionHeight
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.MultipleChoiceQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.PhotoQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.SingleChoiceQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.SingleChoiceQuestionAvatar
import com.kapirti.pomodorotechnique_timemanagementmethod.past.presentation.edit.question.SingleChoiceQuestionLang

@Composable
fun DisplayNameQuestion(
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestion(
        titleResourceId = AppText.display_name,
        directionsResourceId = AppText.display_name,
        text = AppText.display_name,
        value = displayName,
        onValueChange = onDisplayNameChange,
        modifier = modifier,
    )
}

@Composable
fun NameSurnameQuestion(
    name: String,
    surname: String,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestionDouble(
        titleResourceId = AppText.name_and_surname,
        directionsResourceId = AppText.name_and_surname,
        textFirst = AppText.name,
        textSecond = AppText.surname,
        valueFirst = name,
        valueSecond = surname,
        onFirstChange = onNameChange,
        onSecondChange = onSurnameChange,
        modifier = modifier,
    )
}

@Composable
fun GenderQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        possibleAnswers = com.kapirti.pomodorotechnique_timemanagementmethod.past.model.QChatRepo.getGender(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
/**
@Composable
fun BirthdayQuestion(
    birthday: String?,
    onBirthdayChange: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    /** DateQuestion(
    titleResourceId = AppText.birthday,
    directionsResourceId = AppText.select_date,
    birthday = birthday,
    onBirthdayChange = onBirthdayChange,
    modifier = modifier,
    )*/
}*/

@Composable
fun AvatarQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    SingleChoiceQuestionAvatar(
        possibleAnswers = com.kapirti.pomodorotechnique_timemanagementmethod.past.model.QChatRepo.getAvatar(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )
}

@Composable
fun FreeTimeQuestion(
    selectedAnswers: List<String>,
    onOptionSelected: (selected: Boolean, answer: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    MultipleChoiceQuestion(
        titleResourceId = AppText.in_my_free_time,
        directionsResourceId = AppText.select_all,
        possibleAnswers = com.kapirti.pomodorotechnique_timemanagementmethod.past.model.QChatRepo.getHobbies(),
        selectedAnswers = selectedAnswers,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun DescriptionQuestion(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestionHeight(
        titleResourceId = AppText.tell_us_about_you,
        directionsResourceId = AppText.tell_us_about_you,
        text = AppText.description,
        value = description,
        onValueChange = onDescriptionChange,
        modifier = modifier,
    )
}

@Composable
fun TakeSelfieQuestion(
    imageUri: Uri?,
    onPhotoTaken: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoQuestion(
        titleResourceId = AppText.selfie_skills,
        imageUri = imageUri,
        onPhotoTaken = onPhotoTaken,
        modifier = modifier,
    )
}


@Composable
fun DeleteQuestion(
    delete: String,
    onDeleteChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldQuestionHeight(
        titleResourceId = AppText.delete_account_title,
        directionsResourceId = AppText.delete_account_description,
        text = AppText.why_delete_account,
        value = delete,
        onValueChange = onDeleteChange,
        modifier = modifier,
    )
}

@Composable
fun FeedbackQuestion(
    feedback: String,
    onFeedbackChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestionHeight(
        titleResourceId = AppText.feedback,
        directionsResourceId = AppText.feedback_description,
        text = AppText.feedback,
        value = feedback,
        onValueChange = onFeedbackChange,
        modifier = modifier,
    )
}

@Composable
fun LangQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionLang(
        possibleAnswers = com.kapirti.pomodorotechnique_timemanagementmethod.past.model.QChatRepo.getLangs(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
*/
