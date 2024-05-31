package com.kapirti.ira.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kapirti.ira.R.drawable as AppIcon
import androidx.compose.foundation.background
import com.kapirti.ira.ui.theme.ToolbarBackgroundBlueColor

@Composable
fun GoogleSignInRow(
    @StringRes text: Int,
    onClick: () -> Unit,
){
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() }
            .background(ToolbarBackgroundBlueColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){

        Image(
            painter = painterResource(id = AppIcon.ic_google_logo),
            contentDescription = null,
            Modifier
                .size(40.dp)
                .padding(3.dp)
        )
        Spacer(Modifier.width(5.dp))
        Text(stringResource(text))
    }
}
