package com.zepi.social_chat_food.iraaa.core.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.zepi.social_chat_food.R.string as AppText
import com.zepi.social_chat_food.iraaa.core.constants.Cons.PRIVACY_POLICY_CODE
import com.zepi.social_chat_food.iraaa.core.constants.Cons.SHARE_CODE

class SettingsRepository(private val context: Context) {

    fun share() {
        val intent = Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, SHARE_CODE)
        intent.type="text/plain"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(Intent.createChooser(intent, context.getString(AppText.app_name)))
    }

    fun rate() {
        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SHARE_CODE))
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }

    fun privacyPolicy() {
        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_CODE))
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}
