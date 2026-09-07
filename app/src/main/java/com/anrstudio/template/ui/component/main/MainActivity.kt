package com.anrstudio.template.ui.component.main

import android.os.Handler
import android.os.Looper
import com.anrstudio.template.BuildConfig
import com.anrstudio.template.R
import com.anrstudio.template.ads.AdRemoteConfig
import com.anrstudio.template.ads.RemoteConfigUtils
import com.anrstudio.template.ads.banner_home
import com.anrstudio.template.data.model.ForceUpdateConfig
import com.anrstudio.template.databinding.ActivityMainBinding
import com.anrstudio.template.ui.bases.BannerConfig
import com.anrstudio.template.ui.bases.BaseActivityWithBanner
import com.anrstudio.template.ui.bases.ConsentHandler
import com.anrstudio.template.ui.component.main.dialog.ForceUpdateDialog
import com.anrstudio.template.ui.component.main.dialog.NoInternetDialog
import com.anrstudio.template.utils.ConnectionLiveData
import com.anrstudio.template.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivityWithBanner<ActivityMainBinding>() {

    override val bannerConfig = BannerConfig(AdRemoteConfig.banner_home, true)

    private lateinit var consentHandler: ConsentHandler
    private val delayHandler = Handler(Looper.getMainLooper())
    private var delayRunnable: Runnable? = null
    private lateinit var noInternetDialog: NoInternetDialog
    private lateinit var forceUpdateDialog: ForceUpdateDialog
    private var cachedForceUpdateConfig: ForceUpdateConfig? = null

    override fun getLayoutActivity(): Int = R.layout.activity_main

    override fun initViews() {
        super.initViews()
        noInternetDialog = NoInternetDialog(this)
        forceUpdateDialog = ForceUpdateDialog(this)
        checkInternet()
        initConsentHandler()
        checkConsentStatus()
        maybeShowForceUpdateDialog()
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


    override fun onDestroy() {
        super.onDestroy()
        if (::consentHandler.isInitialized) {
            consentHandler.clear()
        }
        delayRunnable?.let {
            delayHandler.removeCallbacks(it)
        }
        noInternetDialog.dismiss()
        forceUpdateDialog.dismiss()
    }
}
