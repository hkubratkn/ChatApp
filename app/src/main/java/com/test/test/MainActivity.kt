package com.test.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.net.toUri
import com.test.test.ui.ShortcutParams
import com.test.test.ui.TestApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //installSplashScreen()
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        super.onCreate(savedInstanceState)

        val bundle = intent!!.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                val value = bundle[key]
                android.util.Log.d("myTag", "Main activity Key: $key Value: $value")
                if (key == "theSender") {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.setAction(Intent.ACTION_VIEW)
                    intent.setData(("https://socialite.google.com/chat/" + value.toString()).toUri())
                    startActivity(intent)
                }
            }
        }

        setContent {

            TestApp(
                shortcutParams = extractShortcutParams(intent),
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.saveIsOnline()
    }
    override fun onPause() {
        super.onPause()
        viewModel.saveLastSeen()
    }

    private fun extractShortcutParams(intent: Intent?): ShortcutParams? {
        if (intent == null || intent.action != Intent.ACTION_SEND) return null
        val shortcutId = intent.getStringExtra(
            ShortcutManagerCompat.EXTRA_SHORTCUT_ID,
        ) ?: return null
        val text = intent.getStringExtra(Intent.EXTRA_TEXT) ?: return null
        return ShortcutParams(shortcutId, text)
    }
}
