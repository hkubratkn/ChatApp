package com.zepi.social_chat_food
/**
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.zepi.social_chat_food.MainViewModel
import com.zepi.social_chat_food.iraaa.QChatApp
import com.zepi.social_chat_food.iraaa.core.data.NetworkMonitor
import com.zepi.social_chat_food.iraaa.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var mInterstitialAd: InterstitialAd? = null


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
val theme by viewModel.theme

val isDarkTheme = when (theme) {
Theme.Dark -> true
Theme.Light -> false
}

val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
QChatApp(
widthSizeClass = widthSizeClass,
networkMonitor = networkMonitor,
isDarkTheme = isDarkTheme,
showInterstitialAds = { }//showInterstitialAd() }
)
           // inAppReview(this)
        }
    }
}

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
            loadInterstialAd()
        } else {
            loadInterstialAd()
        }
    }

    private fun loadInterstialAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            ADS_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }
}

private fun inAppReview(context: Context) {
    val manager = ReviewManagerFactory.create(context)
    val request = manager.requestReviewFlow()

    request.addOnCompleteListener { request ->
        if(request.isSuccessful) {
            val reviewInfo = request.result
            val flow = manager.launchReviewFlow(context as Activity, reviewInfo!!)
            flow.addOnCompleteListener { _ -> }
        }
    }
}*/


/**
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.review.ReviewManagerFactory
import com.kapirti.ira.oldproject.core.constants.Constants.ADS_INTERSTITIAL_ID
import com.kapirti.ira.core.data.NetworkMonitor
import com.kapirti.ira.oldproject.MainViewModel
import com.kapirti.ira.oldproject.QChatApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity :  AppCompatActivity() {
private var mInterstitialAd: InterstitialAd? = null

@Inject
lateinit var networkMonitor: NetworkMonitor

private val viewModel: MainViewModel by viewModels()

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContent {
val theme by viewModel.theme

val isDarkTheme = when (theme) {
com.kapirti.ira.model.Theme.Dark -> true
com.kapirti.ira.model.Theme.Light -> false
}


QChatApp(
networkMonitor = networkMonitor,
isDarkTheme = isDarkTheme,
showInterstialAd = { showInterstitialAd()}
)
inAppReview(this)
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

private fun showInterstitialAd(){
if (mInterstitialAd != null) {
mInterstitialAd?.show(this)
loadInterstialAd()
} else {
loadInterstialAd()
}
}

private fun loadInterstialAd() {
var adRequest = AdRequest.Builder().build()

InterstitialAd.load(
this,
ADS_INTERSTITIAL_ID,
adRequest,
object : InterstitialAdLoadCallback() {
override fun onAdFailedToLoad(adError: LoadAdError) {
mInterstitialAd = null
}

override fun onAdLoaded(interstitialAd: InterstitialAd) {
mInterstitialAd = interstitialAd
}
}
)
}
}

private fun inAppReview(context: Context) {
val manager = ReviewManagerFactory.create(context)
val request = manager.requestReviewFlow()

request.addOnCompleteListener { request ->
if(request.isSuccessful) {
val reviewInfo = request.result
val flow = manager.launchReviewFlow(context as Activity, reviewInfo!!)
flow.addOnCompleteListener { _ -> }
}
}
}*/

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.glance.appwidget.updateAll
import com.zepi.social_chat_food.core.data.NetworkMonitor
import com.zepi.social_chat_food.ui.ShortcutParams
import com.zepi.social_chat_food.model.Theme
import com.zepi.social_chat_food.soci.widget.SociaLiteAppWidget
import com.zepi.social_chat_food.ui.ZepiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var networkMonitor: NetworkMonitor


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        super.onCreate(savedInstanceState)
        runBlocking { SociaLiteAppWidget().updateAll(this@MainActivity) }
        setContent {

            val theme by viewModel.theme

            val isDarkTheme = when (theme) {
                Theme.Dark -> true
                Theme.Light -> false
            }

            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            ZepiApp(
                shortcutParams = extractShortcutParams(intent),
                widthSizeClass = widthSizeClass,
                networkMonitor = networkMonitor,
                isDarkTheme = isDarkTheme,
                showInterstitialAds = { }//showInterstitialAd() }
            )

        }
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

