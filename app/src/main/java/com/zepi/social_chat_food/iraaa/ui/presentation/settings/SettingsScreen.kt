package com.zepi.social_chat_food.iraaa.ui.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.zepi.social_chat_food.iraaa.common.composable.DangerousCardEditor
import com.zepi.social_chat_food.iraaa.common.composable.DialogCancelButton
import com.zepi.social_chat_food.iraaa.common.composable.DialogConfirmButton
import com.zepi.social_chat_food.iraaa.common.composable.RegularCardEditor
import com.zepi.social_chat_food.iraaa.common.composable.ThemeCardEditor
import com.zepi.social_chat_food.iraaa.common.ext.card
import com.zepi.social_chat_food.iraaa.common.ext.spacer
import com.zepi.social_chat_food.iraaa.core.constants.Cons.DEFAULT_LANGUAGE_CODE
import com.zepi.social_chat_food.iraaa.model.Theme

@Composable
fun SettingsScreen(
    navigateEdit: () -> Unit,
    restartApp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.theme
    val profile = viewModel.profile.collectAsState(initial = null)
    var showSignOutDialog by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RegularCardEditor(
            stringResource(AppText.share),
            Icons.Default.Share,
            "",
            Modifier.card()
        ) { viewModel.share() }
        RegularCardEditor(
            stringResource(AppText.rate),
            Icons.Default.StarRate,
            "",
            Modifier.card()
        ) { viewModel.rate() }
        RegularCardEditor(
            stringResource(AppText.privacy_policy),
            Icons.Default.PrivacyTip,
            "",
            Modifier.card()
        ) { viewModel.privacyPolicy() }
        RegularCardEditor(
            stringResource(AppText.feedback),
            Icons.Default.Feedback,
            "",
            Modifier.card(),
        ) { viewModel.onFeedbackClick(navigateEdit)}
        RegularCardEditor(
            viewModel.lang ?: DEFAULT_LANGUAGE_CODE,
            Icons.Default.Flag,
            "",
            Modifier.card()
        ) { viewModel.onLangClick(navigateEdit)}
        ThemeCardEditor(
            title = AppText.dark_mode,
            content = "",
            checked = theme == Theme.Dark,
            onCheckedChange = {
                viewModel.onThemeChanged(if (it) Theme.Dark else Theme.Light)
            },
            modifier = Modifier.card()
        )
        Spacer(modifier = Modifier.spacer())

        if (viewModel.hasUser) {
            RegularCardEditor(
                stringResource(AppText.sign_out),
                Icons.Default.Logout,
                "",
                Modifier.card()
            ) { showSignOutDialog = true }
            DangerousCardEditor(
                stringResource(AppText.delete_my_account),
                Icons.Default.Delete,
                "",
                Modifier.card()
            ) { viewModel.onDeleteClick(navigateEdit)}
        }
    }

    if (showSignOutDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showSignOutDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    profile.value?.let { viewModel.onSignOutClick(restartApp = restartApp, profile = it) }
                    showSignOutDialog = false
                }
            },
            onDismissRequest = { showSignOutDialog = false }
        )
    }
}