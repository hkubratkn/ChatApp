package com.test.test.ui.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.test.test.R.string as AppText
import com.test.test.R.drawable as AppIcon
import com.test.test.common.composable.BasicButton
import com.test.test.common.ext.basicButton
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navigateAndPopUpSplashToHome: () -> Unit,
    navigateAndPopUpSplashToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Text(text = stringResource(AppText.generic_error))

            BasicButton(AppText.try_again, Modifier.basicButton(), true) {
                viewModel.onAppStart(navigateAndPopUpSplashToHome = navigateAndPopUpSplashToHome,
                    navigateAndPopUpSplashToLogin = navigateAndPopUpSplashToLogin)
            }
        } else {
            Image(painter = painterResource(id = AppIcon.ic_launcher_background), contentDescription = stringResource(AppText.app_name), modifier = Modifier.padding(20.dp))
        }
    }

    LaunchedEffect(true) {
        delay(2000)
        viewModel.onAppStart(navigateAndPopUpSplashToHome = navigateAndPopUpSplashToHome,
            navigateAndPopUpSplashToLogin = navigateAndPopUpSplashToLogin)
    }
}
