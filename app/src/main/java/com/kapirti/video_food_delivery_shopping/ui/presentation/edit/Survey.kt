package com.kapirti.video_food_delivery_shopping.ui.presentation.edit

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.video_food_delivery_shopping.R.string as AppText
import com.kapirti.video_food_delivery_shopping.model.QChatRepo
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.FieldQuestion
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.FieldQuestionDouble
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.FieldQuestionHeight
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.MultipleChoiceQuestion
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.PhotoQuestion
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.SingleChoiceQuestion
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.SingleChoiceQuestionAvatar
import com.kapirti.video_food_delivery_shopping.ui.presentation.edit.question.SingleChoiceQuestionLang

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
        possibleAnswers = QChatRepo.getGender(),
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
        possibleAnswers = QChatRepo.getAvatar(),
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
        possibleAnswers = QChatRepo.getHobbies(),
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
        possibleAnswers = QChatRepo.getLangs(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}
