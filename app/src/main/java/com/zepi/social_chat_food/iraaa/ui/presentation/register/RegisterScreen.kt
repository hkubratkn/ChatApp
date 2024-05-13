package com.zepi.social_chat_food.iraaa.ui.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zepi.social_chat_food.R.string as AppText
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import com.zepi.social_chat_food.iraaa.common.composable.AdsBannerToolbar
import com.zepi.social_chat_food.iraaa.common.composable.BasicButton
import com.zepi.social_chat_food.iraaa.common.composable.BasicTextButton
import com.zepi.social_chat_food.iraaa.common.composable.EmailField
import com.zepi.social_chat_food.iraaa.common.composable.HeaderText
import com.zepi.social_chat_food.iraaa.common.composable.HyperlinkText
import com.zepi.social_chat_food.iraaa.common.composable.PasswordField
import com.zepi.social_chat_food.iraaa.common.composable.RepeatPasswordField
import com.zepi.social_chat_food.iraaa.common.ext.basicButton
import com.zepi.social_chat_food.iraaa.common.ext.fieldModifier
import com.zepi.social_chat_food.iraaa.common.ext.smallSpacer
import com.zepi.social_chat_food.iraaa.common.ext.textButton
import com.zepi.social_chat_food.iraaa.core.constants.ConsAds.ADS_REGISTER_BANNER_ID

@Composable
fun RegisterScreen(
    registerToLogin: () -> Unit,
    showInterstitialAd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

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
            hyperlink = listOf("http://kapirti.lovestoblog.com/","http://kapirti.lovestoblog.com/"),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )

        BasicButton(text = AppText.register, Modifier.basicButton(), uiState.button) {
          //  viewModel.onRegisterClick(openAndPopUp)
            showInterstitialAd()
        }

        Spacer(modifier = Modifier.smallSpacer())

        BasicTextButton(AppText.already_have_an_account, Modifier.textButton()) {
            showInterstitialAd()
            registerToLogin()
        }
    }
}