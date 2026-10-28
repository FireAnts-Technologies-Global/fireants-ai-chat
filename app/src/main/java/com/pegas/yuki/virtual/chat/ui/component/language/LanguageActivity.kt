package com.pegas.yuki.virtual.chat.ui.component.language

import com.anrstudio.ads.ads.wrapper.ApNativeAd
import com.anrstudio.config.utils.setOnAdminAdToggleListener
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ads.AdsManager
import com.pegas.yuki.virtual.chat.ads.RemoteConfigUtils
import com.pegas.yuki.virtual.chat.ads.populateNativeAdView
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.app.AppConstants.DEFAULT_TIME_DELAY_SHOW_LANGUAGE_DONE_BUTTON
import com.pegas.yuki.virtual.chat.databinding.ActivityLanguageBinding
import com.pegas.yuki.virtual.chat.ui.bases.BaseActivity
import com.pegas.yuki.virtual.chat.ui.bases.NavigationBarConfig
import com.pegas.yuki.virtual.chat.ui.bases.ext.click
import com.pegas.yuki.virtual.chat.ui.bases.ext.goneView
import com.pegas.yuki.virtual.chat.ui.bases.ext.isNetwork
import com.pegas.yuki.virtual.chat.ui.bases.ext.visibleView
import com.pegas.yuki.virtual.chat.ui.component.language.adapter.LanguageAdapter
import com.pegas.yuki.virtual.chat.ui.component.language.data.LanguageData
import com.pegas.yuki.virtual.chat.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private var timeDelayDoneButton = DEFAULT_TIME_DELAY_SHOW_LANGUAGE_DONE_BUTTON

    override val navigationBarConfig: NavigationBarConfig
        get() = NavigationBarConfig(
            isVisible = RemoteConfigUtils.getOnShowNavigationButton(),
            applyPadding = RemoteConfigUtils.getOnShowNavigationButton()
        )

    override fun getLayoutActivity() = R.layout.activity_language

    private val fromSetting
        get() = intent.getBooleanExtra(AppConstants.KEY_SETTING, false)
    private var shouldDelayDoneButton = true
    private var selectedIso = LanguageData.defaultLanguage.iso
    private var hasVisibleLanguageAd = false
    private var isLanguageClickRequested = false
    private var hasAppliedLanguageClickAd = false

    private val languageAdapter: LanguageAdapter by lazy {
        LanguageAdapter(
            onItemLanguageClick = {
                if (!fromSetting) {
                    listenLanguageClickAd()
                }
                enableDoneButton()
                selectedIso = it.iso
                LanguageData.selectLanguage(it.iso)
                resubmitLanguageData()
            }
        )
    }

    override fun initViews() {
        LanguageData.clearSelection()
        forceEnableShowAd()
        shouldDelayDoneButton = RemoteConfigUtils.shouldDelayLanguageDoneButton()
        timeDelayDoneButton = RemoteConfigUtils.getTimeDelayButtonDoneLanguage()
        initAdapter()
        initLayout()

        mBinding.root.postDelayed({
            initAds()
        }, 100L)
    }

    override fun observeData() {
        super.observeData()
        if (!fromSetting) {
            listenLanguageAd()
        } else {
            hideLanguageAd()
        }
    }

    override fun onClickViews() {
        mBinding.imgBack.click {
            onBackPressed()
        }
        mBinding.tvDone.click {
            val iso = selectedIso
            appSharedPref.languageCode = iso
            startNextActivity()
        }

    }

    private fun initAdapter() {
        mBinding.recyclerView.adapter = languageAdapter
        resubmitLanguageData()
    }

    private fun initLayout() {
        mBinding.tvDone.visibleView()

        if (fromSetting) {
            mBinding.tvDone.isEnabled = true
            timeDelayDoneButton = 0
            mBinding.imgBack.visibleView()
        } else {
            mBinding.tvDone.isEnabled = false
            mBinding.imgBack.goneView()
        }
    }

    private fun initAds() {
        if (fromSetting) {
            hideLanguageAd()
        } else {
            AdsManager.loadNativeOnboarding4(
                this,
                R.layout.layout_native_onboarding
            )
        }
    }

    private fun listenLanguageAd() {
        AdsManager.nativeLanguageAdStateLive.observe(this) { state ->
            renderLanguageAdState(state, keepVisibleAd = isLanguageClickRequested)
        }
        AdsManager.nativeLanguageAdLive.observe(this) { ad ->
            if (ad != null && !hasAppliedLanguageClickAd) showNativeLanguage(ad)
        }
    }

    private fun listenLanguageClickAd() {
        isLanguageClickRequested = true
        AdsManager.nativeLanguageClickAdStateLive.removeObservers(this)
        AdsManager.nativeLanguageClickAdStateLive.observe(this) { state ->
            renderLanguageAdState(state, keepVisibleAd = true)
        }
        AdsManager.nativeLanguageClickAdLive.observe(this) { ad ->
            if (ad != null) {
                hasAppliedLanguageClickAd = true
                showNativeLanguage(ad)
            }
        }
    }

    private fun renderLanguageAdState(
        state: AdsManager.NativeAdLoadState,
        keepVisibleAd: Boolean,
    ) {
        when (state) {
            AdsManager.NativeAdLoadState.IDLE,
            AdsManager.NativeAdLoadState.LOADING -> {
                if (!keepVisibleAd || !hasVisibleLanguageAd) showLanguageAdLoading()
            }

            AdsManager.NativeAdLoadState.LOADED -> Unit
            AdsManager.NativeAdLoadState.FAILED -> {
                if (!keepVisibleAd || !hasVisibleLanguageAd) hideLanguageAd()
            }
        }
    }

    private fun showLanguageAdLoading() {
        if (fromSetting) return
        mBinding.flAds.visibleView()
        mBinding.flAdContent.goneView()
        mBinding.shimmerAds.shimmerNativeLarge.visibleView()
        mBinding.shimmerAds.shimmerNativeLarge.startShimmer()
    }

    private fun hideLanguageAd() {
        mBinding.shimmerAds.shimmerNativeLarge.stopShimmer()
        mBinding.flAdContent.goneView()
        mBinding.flAds.goneView()
    }

    private fun showNativeLanguage(ad: ApNativeAd) {
        if (fromSetting) {
            hideLanguageAd()
            return
        }
        if (!isNetwork()) {
            hideLanguageAd()
            return
        }
        mBinding.flAds.visibleView()
        mBinding.flAdContent.visibleView()
        hasVisibleLanguageAd = true
        populateNativeAdView(
            this,
            ad,
            mBinding.flAdContent,
            mBinding.shimmerAds.shimmerNativeLarge
        )
    }

    private fun enableDoneButton() {
        if (mBinding.tvDone.isEnabled) return

        if (shouldDelayDoneButton && timeDelayDoneButton > 0) {
            mBinding.tvDone.postDelayed({
                mBinding.tvDone.isEnabled = true
            }, timeDelayDoneButton)
        } else {
            mBinding.tvDone.isEnabled = true
        }
    }

    private fun resubmitLanguageData() {
        languageAdapter.submitList(LanguageData.languages)
    }

    private fun startNextActivity() {
        if (fromSetting) {
            Routes.startMainActivity(this)
        } else {
            appSharedPref.firstLanguage = false
            Routes.startOnBoardingActivity(this)
        }
        finish()
    }

    private fun forceEnableShowAd() {
//        mBinding.tvTitle.setOnAdminAdToggleListener {
//            Routes.startSplashActivity(this@LanguageActivity)
//            finish()
//        }
    }
}
