package com.kapirti.video_food_delivery_shopping.ui.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kapirti.video_food_delivery_shopping.R.string as AppText
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapirti.video_food_delivery_shopping.common.composable.AdsBannerToolbar
import com.kapirti.video_food_delivery_shopping.common.composable.BasicButton
import com.kapirti.video_food_delivery_shopping.common.composable.BasicTextButton
import com.kapirti.video_food_delivery_shopping.common.composable.EmailField
import com.kapirti.video_food_delivery_shopping.common.composable.HeaderText
import com.kapirti.video_food_delivery_shopping.common.composable.HyperlinkText
import com.kapirti.video_food_delivery_shopping.common.composable.PasswordField
import com.kapirti.video_food_delivery_shopping.common.composable.RepeatPasswordField
import com.kapirti.video_food_delivery_shopping.common.ext.basicButton
import com.kapirti.video_food_delivery_shopping.common.ext.fieldModifier
import com.kapirti.video_food_delivery_shopping.common.ext.smallSpacer
import com.kapirti.video_food_delivery_shopping.common.ext.textButton
import com.kapirti.video_food_delivery_shopping.core.constants.ConsAds.ADS_REGISTER_BANNER_ID

@Composable
fun RegisterScreen(
    navigateAndPopUpRegisterToEdit: () -> Unit,
    registerToLogin: () -> Unit,
    showInterstitialAds: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()
    val email_error = stringResource(AppText.email_error)
    val password_error = stringResource(AppText.password_error)
    val password_match_error = stringResource(AppText.password_match_error)

    AdsBannerToolbar(ads = ADS_REGISTER_BANNER_ID)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            HeaderText(text = AppText.register)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(imageVector = Icons.Default.PersonAddAlt1, contentDescription = null)
        }

        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
        RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier)

        Spacer(modifier = Modifier.smallSpacer())

        HyperlinkText(
            fullText = "By clicking Register, you are accepting the Terms of use and Privacy Policy.",
            linkText = listOf("Terms of use", "Privacy Policy"),
            hyperlink = listOf(
                "https://kapirti.com/app/foood/termsandconditions.html",
                "https://kapirti.com/app/foood/privacypolicy.html"
            ),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        BasicButton(text = AppText.register, Modifier.basicButton(), uiState.button) {
            viewModel.onRegisterClick(
                onShowSnackbar = onShowSnackbar,
                navigateAndPopUpRegisterToEdit = navigateAndPopUpRegisterToEdit,
                email_error = email_error,
                password_error = password_error,
                password_match_error = password_match_error,
            )
            showInterstitialAds()
        }

        Spacer(modifier = Modifier.smallSpacer())

        BasicTextButton(AppText.already_have_an_account, Modifier.textButton()) {
            showInterstitialAds()
            registerToLogin()
        }
    }
}
