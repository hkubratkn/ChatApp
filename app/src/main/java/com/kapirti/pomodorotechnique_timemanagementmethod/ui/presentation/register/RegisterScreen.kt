package com.kapirti.pomodorotechnique_timemanagementmethod.ui.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kapirti.pomodorotechnique_timemanagementmethod.R.string as AppText
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
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.AdsBannerToolbar
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.BasicTextButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.EmailField
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HeaderText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.HyperlinkText
import com.kapirti.pomodorotechnique_timemanagementmethod.common.composable.PasswordField
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.basicButton
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.fieldModifier
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.smallSpacer
import com.kapirti.pomodorotechnique_timemanagementmethod.common.ext.textButton
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.Cons.SPLASH_TIMEOUT
import com.kapirti.pomodorotechnique_timemanagementmethod.core.constants.ConsAds.ADS_REGISTER_BANNER_ID
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navigateAndPopUpRegisterToEdit: () -> Unit,
    registerToLogin: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()
    val email_error = stringResource(AppText.email_error)
    val password_error = stringResource(AppText.password_error)
    val password_match_error = stringResource(AppText.password_match_error)
    val context = LocalContext.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { AdsBannerToolbar(ads = ADS_REGISTER_BANNER_ID) }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
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

            EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
            PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
            Spacer(modifier = Modifier.smallSpacer())

            HyperlinkText(
                fullText = "By clicking Register, you are accepting the Terms of use and Privacy Policy.",
                linkText = listOf("Terms of use", "Privacy Policy"),
                hyperlink = listOf(
                    "https://kapirti.com/app/quickchat/termsandconditions.html",
                    "https://kapirti.com/app/quickchat/privacypolicy.html"
                ),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            BasicButton(text = AppText.register, Modifier.basicButton(), uiState.button) {
                viewModel.onRegisterClick(
                    snackbarHostState = snackbarHostState,
                    navigateAndPopUpRegisterToEdit = navigateAndPopUpRegisterToEdit,
                    email_error = email_error,
                    password_error = password_error,
                    password_match_error = password_match_error,
                    context = context
                )
            }
            Spacer(modifier = Modifier.smallSpacer())


            BasicTextButton(AppText.already_have_an_account, Modifier.textButton()) {
                registerToLogin()
            }
        }
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        viewModel.initialize(context)
    }
}
