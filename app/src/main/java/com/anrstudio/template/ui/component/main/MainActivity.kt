package com.anrstudio.template.ui.component.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.anrstudio.template.ads.AdRemoteConfig
import com.anrstudio.template.ads.RemoteConfigUtils
import com.anrstudio.template.ads.banner_all
import com.anrstudio.template.data.model.ForceUpdateConfig
import com.anrstudio.template.ui.bases.BannerConfig
import com.anrstudio.template.ui.bases.ConsentHandler
import com.anrstudio.template.ui.bases.compose.activity.BaseComposeActivityWithBanner
import com.anrstudio.template.ui.bases.ext.showRateDialog
import com.anrstudio.template.ui.bases.navigation.AppNavHost
import com.anrstudio.template.ui.component.main.dialog.ForceUpdateDialog
import com.anrstudio.template.ui.component.main.dialog.NoInternetDialog
import com.anrstudio.template.ui.component.rate.RatePromptPolicy
import com.anrstudio.template.utils.ConnectionLiveData
import com.anrstudio.template.utils.Routes
import com.pegas.aura.aigirlfriend.soul.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseComposeActivityWithBanner() {

    override val bannerConfig = BannerConfig(AdRemoteConfig.banner_all, false)
    private lateinit var consentHandler: ConsentHandler
    private val delayHandler = Handler(Looper.getMainLooper())
    private var delayRunnable: Runnable? = null
    private var rateDelayRunnable: Runnable? = null
    private lateinit var noInternetDialog: NoInternetDialog
    private lateinit var forceUpdateDialog: ForceUpdateDialog
    private var cachedForceUpdateConfig: ForceUpdateConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color(0xFF0F0817).toArgb(),
                darkScrim = Color(0xFF0F0817).toArgb()
            )
        )
        noInternetDialog = NoInternetDialog(this)
        forceUpdateDialog = ForceUpdateDialog(this)
        checkInternet()
        initConsentHandler()
        checkConsentStatus()
        maybeShowForceUpdateDialog()
        delayShowRateDialogAfterEngagement()
    }

    @Composable
    override fun Content() {
        AppNavHost()
    }

    private fun initConsentHandler() {
        consentHandler = ConsentHandler(
            activity = this,
            appSharedPref = appSharedPref,
            trackingSuffix = 2,
            onConsentFlowCompleted = { Timber.d("Consent flow completed") },
            onConsentSuccess = { canPersonalized ->
                if (canPersonalized) {
                    Routes.startSplashActivity(this)
                    finish()
                }
            },
            onNotUsingAdConsent = {
                appSharedPref.isUserGlobal = true
            }
        )
    }

    private fun checkConsentStatus() {
        if (appSharedPref.isConfirmConsent.not() && appSharedPref.isUserGlobal.not()) {
            delayShowConsentDialog()
        }
    }

    private fun delayShowConsentDialog() {
        if (!RemoteConfigUtils.getOnShowDialogConsent()) {
            return
        }
        delayRunnable = Runnable {
            consentHandler.requestConsent()
        }
        delayHandler.postDelayed(delayRunnable!!, 5000L)
    }


    private fun checkInternet() {
        ConnectionLiveData(this).observe(this) { isNetwork ->
            if (isNetwork) {
                Timber.d("network on")
                noInternetDialog.dismiss()
            } else {
                Timber.d("network off")
                noInternetDialog.show()
            }
        }
    }


    private fun maybeShowForceUpdateDialog() {
        val config = RemoteConfigUtils.getForceUpdateConfig() ?: return
        if (config.storeLink.isBlank()) return
        val needsUpdate = BuildConfig.VERSION_CODE < config.minVersionCode
        if (needsUpdate || config.force) {
            cachedForceUpdateConfig = config
            forceUpdateDialog.show(config)
        }
    }

    private fun delayShowRateDialogAfterEngagement() {
        rateDelayRunnable = Runnable {
            maybeShowRateDialogAfterEngagement()
        }
        delayHandler.postDelayed(rateDelayRunnable!!, RatePromptPolicy.MAIN_DELAY_MS)
    }

    private fun maybeShowRateDialogAfterEngagement() {
        if (!RatePromptPolicy.canShowAfterAppEngagement(appSharedPref)) return

        RatePromptPolicy.markShownInSession(appSharedPref)
        showRateDialog(this@MainActivity, false) {
            appSharedPref.isRate = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::consentHandler.isInitialized) {
            consentHandler.clear()
        }
        delayRunnable?.let {
            delayHandler.removeCallbacks(it)
        }
        rateDelayRunnable?.let {
            delayHandler.removeCallbacks(it)
        }
        noInternetDialog.dismiss()
        forceUpdateDialog.dismiss()
    }
}
