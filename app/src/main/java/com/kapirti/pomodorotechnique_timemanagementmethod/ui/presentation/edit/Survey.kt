package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
import androidx.annotation.StringRes
import com.kapirti.pomodorotechnique_timemanagementmethod.model.QChatRepo
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question.FieldQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question.FieldQuestionHeight
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question.PhotoQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question.PomoQuestion
import com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.edit.question.SingleChoiceQuestionCountry


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
fun CountryQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionCountry(
        possibleAnswers = QChatRepo.getCountries(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun PomoValueQuestion(
    value: String,
    minusBtnState: Boolean,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    PomoQuestion(
        titleResourceId = AppText.pomo,
        directionsResourceId = AppText.pomo,
        value = value,
        minusBtnState = minusBtnState,
        plusBtnState = true,
        modifier = modifier,
        onMinusClick = onMinusClick,
        onPlusClick = onPlusClick,
    )
}
