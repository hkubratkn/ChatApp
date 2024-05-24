package com.kapirti.ira.ui.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import com.kapirti.ira.R.string as AppText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kapirti.ira.common.composable.DangerousCardEditor
import com.kapirti.ira.common.composable.DialogCancelButton
import com.kapirti.ira.common.composable.DialogConfirmButton
import com.kapirti.ira.common.composable.RegularCardEditor
import com.kapirti.ira.common.composable.ThemeCardEditor
import com.kapirti.ira.common.ext.card
import com.kapirti.ira.common.ext.spacer
import com.kapirti.ira.core.constants.Cons.DEFAULT_LANGUAGE_CODE
import com.kapirti.ira.model.Theme

@Composable
fun SettingsScreen(
    navigateEdit: () -> Unit,
    restartApp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val theme by viewModel.theme
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
                stringResource(AppText.edit_profile),
                Icons.Default.ManageAccounts,
                "", Modifier.card()
            ) { viewModel.onEditProfileClick(navigateEdit) }
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
                    viewModel.onSignOutClick(restartApp = restartApp)
                    showSignOutDialog = false
                }
            },
            onDismissRequest = { showSignOutDialog = false }
        )
    }
}