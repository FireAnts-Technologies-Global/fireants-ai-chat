package com.anrstudio.template.ui.bases.compose.activity

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.anrstudio.ads.billing.AppPurchase
import com.anrstudio.template.ads.AdsManager
import com.anrstudio.template.ui.bases.BannerConfig

abstract class BaseComposeActivityWithBanner : BaseComposeActivity() {

    companion object {
        private const val DISTANCE_TIME_NEED_CHECK_RELOAD_BANNER = 2000L
    }

    abstract val bannerConfig: BannerConfig

    private var timeNeedReloadBanner = 0L
    private var bannerContainer: FrameLayout? = null
    private var reloadBannerHandler: Handler? = null

    private val reloadBannerRunnable: Runnable = object : Runnable {
        override fun run() {
            if (isDestroyed || isFinishing) return
            if (timeNeedReloadBanner < System.currentTimeMillis() && shouldReloadBanner()) {
                loadBanner()
            }

            if (shouldReloadBanner()) {
                reloadBannerHandler?.removeCallbacks(this)
                reloadBannerHandler?.postDelayed(this, DISTANCE_TIME_NEED_CHECK_RELOAD_BANNER)
            } else {
                cleanupHandler()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        reloadBannerIfNeeded()
    }

    override fun onPause() {
        cleanupHandler()
        super.onPause()
    }

    override fun onDestroy() {
        cleanupHandler()
        bannerContainer = null
        super.onDestroy()
    }

    @Composable
    protected fun BannerHost(modifier: Modifier = Modifier) {
        AndroidView(
            factory = { context ->
                FrameLayout(context).also {
                    bannerContainer = it
                    loadBanner()
                }
            },
            modifier = modifier,
            update = { frameLayout ->
                bannerContainer = frameLayout
                if (shouldShowBanner()) {
                    loadBanner()
                } else {
                    frameLayout.removeAllViews()
                    cleanupHandler()
                }
            }
        )
    }

    private fun loadBanner() {
        val frameLayout = bannerContainer
        if (!shouldShowBanner()) {
            timeNeedReloadBanner = 0L
            frameLayout?.removeAllViews()
            cleanupHandler()
            return
        }

        frameLayout?.let {
            if (it.parent is ViewGroup) {
                AdsManager.loadBanner(this, bannerConfig.adUnitConfig, it, bannerConfig.isCollapse)
            }
        }

        val distanceReloadBanner = bannerConfig.adUnitConfig.reloadIntervalSeconds ?: 0
        if (distanceReloadBanner > 0) {
            timeNeedReloadBanner = System.currentTimeMillis() + distanceReloadBanner * 1000L
        }
    }

    private fun reloadBannerIfNeeded() {
        if (shouldReloadBanner()) {
            if (reloadBannerHandler == null) {
                reloadBannerHandler = Handler(Looper.getMainLooper())
            }
            reloadBannerHandler?.postDelayed(
                reloadBannerRunnable,
                DISTANCE_TIME_NEED_CHECK_RELOAD_BANNER
            )
        } else {
            cleanupHandler()
        }
    }

    private fun shouldShowBanner(): Boolean {
        return bannerContainer != null &&
                bannerConfig.adUnitConfig.isEnable &&
                !AppPurchase.getInstance().isPurchased
    }

    private fun shouldReloadBanner(): Boolean {
        val distanceReloadBanner = bannerConfig.adUnitConfig.reloadIntervalSeconds ?: 0
        return shouldShowBanner() && distanceReloadBanner > 0
    }

    private fun cleanupHandler() {
        reloadBannerHandler?.removeCallbacksAndMessages(null)
        reloadBannerHandler = null
    }
}
