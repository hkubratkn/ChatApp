package com.zepi.social_chat_food.iraaa.common.ext

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChromeReaderMode
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.ui.graphics.vector.ImageVector
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.constants.ConsGender.FEMALE
import com.zepi.social_chat_food.iraaa.core.constants.ConsGender.MALE
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.DANCE
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.DRAW
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.PLAY_GAMES
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.READ
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.WATCH_MOVIES
import com.zepi.social_chat_food.iraaa.core.constants.Hobby.WORK_OUT

fun hobbyIcon(text: String): ImageVector {
    return when(text){
        READ -> Icons.Default.ChromeReaderMode
        WORK_OUT -> Icons.Default.SportsMartialArts
        DRAW -> Icons.Default.Draw
        PLAY_GAMES -> Icons.Default.Games
        DANCE -> Icons.Default.Nightlife
        WATCH_MOVIES -> Icons.Default.PlayCircle

        else -> Icons.Default.VideogameAsset
    }
}

fun genderIcon(cons: String): ImageVector{
    return when(cons){
        FEMALE -> Icons.Default.Female
        MALE -> Icons.Default.Male
        else -> Icons.Default.Female
    }
}

fun genderText(cons: String): Int{
    return when(cons){
        FEMALE -> AppText.female
        MALE -> AppText.male
        else -> AppText.female
    }
}
