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

package com.kapirti.video_food_delivery_shopping.ui.presentation.edit

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.video_food_delivery_shopping.common.composable.DialogCancelButton
import com.kapirti.video_food_delivery_shopping.common.composable.DialogConfirmButton
import com.kapirti.video_food_delivery_shopping.common.composable.PasswordField
import com.kapirti.video_food_delivery_shopping.common.ext.fieldModifier
import com.kapirti.video_food_delivery_shopping.R.string as AppText

private const val CONTENT_ANIMATION_DURATION = 300


@Composable
fun EditRoute(
    popUp: () -> Unit,
    restartApp: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
//    viewModel: EditViewModel = hiltViewModel()
) {}
/**
    val surveyScreenData = viewModel.surveyScreenData ?: return
    val uiState by viewModel.uiState
    val context = LocalContext.current

    BackHandler {
        if (!viewModel.onBackPressed()) {
            popUp()
        }
    }

    EditScreen(
        surveyScreenData = surveyScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = { popUp() },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(
            popUp = popUp,
            restartApp = restartApp,
            onShowSnackbar = onShowSnackbar,
            context = context
        ) }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        AnimatedContent(
            targetState = surveyScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = targetState.questionIndex,
                )

                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = "surveyScreenDataAnimation"
        ) { targetState ->

            when (targetState.surveyQuestion) {
                SurveyQuestion.DISPLAY_NAME -> {
                    DisplayNameQuestion(
                        displayName = viewModel.displayName ?: "",
                        onDisplayNameChange = viewModel::onDisplayNameChange,
                        modifier = modifier,
                    )
                }

                SurveyQuestion.NAME_SURNAME -> {
                    NameSurnameQuestion(
                        name = viewModel.name ?: "",
                        surname = viewModel.surname ?: "",
                        onNameChange = viewModel::onNameChange,
                        onSurnameChange = viewModel::onSurnameChange,
                        modifier = modifier,
                    )
                }

                SurveyQuestion.GENDER -> GenderQuestion(
                    selectedAnswer = viewModel.gender,
                    onOptionSelected = viewModel::onGenderChange,
                    modifier = modifier,
                )

                SurveyQuestion.AVATAR -> AvatarQuestion(
                    selectedAnswer = viewModel.avatar,
                    onOptionSelected = viewModel::onAvatarChange,
                    modifier = modifier
                )

                SurveyQuestion.FREE_TIME -> {
                    FreeTimeQuestion(
                        selectedAnswers = viewModel.freeTimeResponse,
                        onOptionSelected = viewModel::onFreeTimeResponse,
                        modifier = modifier,
                    )
                }

                SurveyQuestion.DESCRIPTION -> {
                    DescriptionQuestion(
                        description = viewModel.description ?: "",
                        onDescriptionChange = viewModel::onDescriptionChange,
                        modifier = modifier
                    )
                }

                SurveyQuestion.TAKE_SELFIE -> TakeSelfieQuestion(
                    imageUri = viewModel.selfieUri,
                    onPhotoTaken = viewModel::onSelfieResponse,
                    modifier = modifier,
                )

                SurveyQuestion.DELETE -> DeleteQuestion(
                    delete = viewModel.description ?: "",
                    onDeleteChange = viewModel::onDescriptionChange,
                    modifier = modifier
                )
                SurveyQuestion.FEEDBACK -> FeedbackQuestion(
                    feedback = viewModel.description ?: "",
                    onFeedbackChange = viewModel::onDescriptionChange,
                    modifier = modifier
                )
                SurveyQuestion.LANG -> LangQuestion(
                    selectedAnswer = viewModel.lang,
                    onOptionSelected = viewModel::onLangChange,
                    modifier = modifier
                )
            }
        }
    }
    val empty_password_error = stringResource(id = AppText.empty_password_error)
    if (viewModel.showWarningDialog ?: false) {
        AlertDialog(
            title = {
                Column(){
                    PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())
                    Text(stringResource(AppText.delete_account_title))
                } },
            text = { Text(stringResource(AppText.delete_account_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { popUp()} },
            confirmButton = {
                DialogConfirmButton(AppText.delete_my_account) {
                    viewModel.onDeleteMyAccountClick(
                        restartApp = restartApp, onShowSnackbar = onShowSnackbar,
                        empty_password_error = empty_password_error
                    )
                }
            },
            onDismissRequest = {popUp()}
        )
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }
*/