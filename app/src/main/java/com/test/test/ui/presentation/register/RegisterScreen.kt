package com.test.test.ui.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.test.test.R.string as AppText
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.test.test.common.composable.BasicButton
import com.test.test.common.composable.BasicTextButton
import com.test.test.common.composable.EmailField
import com.test.test.common.composable.HeaderText
import com.test.test.common.composable.HyperlinkText
import com.test.test.common.composable.PasswordField
import com.test.test.common.ext.basicButton
import com.test.test.common.ext.fieldModifier
import com.test.test.common.ext.smallSpacer
import com.test.test.common.ext.textButton
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()
    val email_error = stringResource(AppText.email_error)
    val password_error = stringResource(AppText.password_error)
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HeaderText(text = AppText.register)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.PersonAddAlt1,
                contentDescription = stringResource(
                    id = AppText.register,
                ),
            )
        }

        EmailField(
            value = uiState.email,
            onNewValue = viewModel::onEmailChange,
            modifier = Modifier.fieldModifier(),
            isError = uiState.isErrorEmail
        )
        PasswordField(
            value = uiState.password,
            onNewValue = viewModel::onPasswordChange,
            modifier = Modifier.fieldModifier(),
            isError = uiState.isErrorPassword
        )
        Spacer(modifier = Modifier.smallSpacer())

        BasicButton(text = AppText.register, Modifier.basicButton(), uiState.button) {}
        Spacer(modifier = Modifier.smallSpacer())


        BasicTextButton(AppText.already_have_an_account, Modifier.textButton()) {}
    }
}
