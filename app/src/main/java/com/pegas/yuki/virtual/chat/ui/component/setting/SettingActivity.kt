package com.pegas.yuki.virtual.chat.ui.component.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.fireants.adsdk.admob.AppOpenManager
import com.pegas.yuki.virtual.chat.BuildConfig
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.app.AppConstants
import com.pegas.yuki.virtual.chat.app.ResumeAdsEntryRule
import com.pegas.yuki.virtual.chat.databinding.ActivitySettingBinding
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.usecase.promo.RedeemPromoCodeUseCase
import com.pegas.yuki.virtual.chat.ui.bases.BaseActivity
import com.pegas.yuki.virtual.chat.ui.bases.ext.click
import com.pegas.yuki.virtual.chat.ui.bases.ext.showRateDialog
import com.pegas.yuki.virtual.chat.ui.component.dialog.DialogRedeemPromoCode
import com.pegas.yuki.virtual.chat.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    @Inject
    lateinit var redeemPromoCodeUseCase: RedeemPromoCodeUseCase

    private var titleClickCount = 0
    private var lastTitleClickTime = 0L

    override fun getLayoutActivity(): Int = R.layout.activity_setting

    override fun onClickViews() {
        super.onClickViews()
        mBinding.apply {
            imvBack.click { finish() }
            tvSettingTitle.setOnClickListener { handleTitleEasterEgg() }
            rltLanguage.click {
                val bundle = Bundle()
                bundle.putBoolean(AppConstants.KEY_SETTING, true)
                Routes.startLanguageActivity(this@SettingActivity, bundle)
            }
            rltRate.click { initRate() }
            rltShare.click { shareApp(this@SettingActivity) }
            rltPolicy.click {
                openPrivacyPolicy()
            }
        }
    }

    private fun handleTitleEasterEgg() {
        val now = System.currentTimeMillis()
        if (now - lastTitleClickTime > 2000L) {
            titleClickCount = 1
        } else {
            titleClickCount++
        }
        lastTitleClickTime = now

        if (titleClickCount >= 7) {
            titleClickCount = 0
            showRedeemPromoCodeDialog()
        }
    }

    private fun showRedeemPromoCodeDialog() {
        val dialog = DialogRedeemPromoCode(this) { code, d ->
            d.setLoading(true)
            lifecycleScope.launch {
                when (val result = redeemPromoCodeUseCase(code)) {
                    is AppResult.Success -> {
                        d.setLoading(false)
                        d.dismiss()
                        val coins = result.data.coinsAwarded
                        val message = if (coins > 0) {
                            getString(R.string.promo_code_success_coins, coins)
                        } else {
                            getString(R.string.promo_code_success)
                        }
                        Toast.makeText(this@SettingActivity, message, Toast.LENGTH_LONG).show()
                    }

                    is AppResult.Failure -> {
                        d.setLoading(false)
                        val errorMsg = result.error.customMessage
                            ?: getString(R.string.error_generic)
                        d.showError(errorMsg)
                    }
                }
            }
        }
        dialog.show()
    }

    private fun initRate() {
        val isRate = appSharedPref.isRate
        if (isRate) {
            Toast.makeText(
                this@SettingActivity,
                this@SettingActivity.getString(R.string.txt_thanks_you_for_rating),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            showRateDialog(this@SettingActivity, false) {
                appSharedPref.isRate = true
            }
        }
    }

    private fun shareApp(context: Context) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
            var shareMessage =
                "${context.getString(R.string.app_name)}\n${context.getString(R.string.let_me_recommend)}"
            shareMessage =
                "$shareMessage\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            Handler().postDelayed({
                context.startActivity(
                    Intent.createChooser(
                        shareIntent, context.getString(R.string.share_to)
                    )
                )
            }, 250)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openPrivacyPolicy() {
        val privacyPolicyUrl = AppConstants.LINK_PRIVACY_POLICY
        if (privacyPolicyUrl.isBlank()) {
            Toast.makeText(
                this,
                getString(R.string.setting_privacy_not_configured),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        try {
            val uri = Uri.parse(privacyPolicyUrl)
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            if (browserIntent.resolveActivity(packageManager) != null) {
                startActivity(browserIntent)
                disableAdsResume()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.setting_no_app_open_link),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } catch (e: Exception) {
            Log.e("SettingActivity", "Error opening privacy policy", e)
            Toast.makeText(
                this,
                getString(R.string.setting_unable_open_privacy),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendFeedback(email: String) {
        val intentFeedBack = Intent(Intent.ACTION_SEND)
        intentFeedBack.type = "text/email"
        intentFeedBack.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intentFeedBack.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        intentFeedBack.putExtra(Intent.EXTRA_TEXT, "" + "")
        startActivity(Intent.createChooser(intentFeedBack, "Send Feedback:"))
    }

    override fun onResume() {
        super.onResume()

        enableAdsResume()
    }

    private fun disableAdsResume() {
        AppOpenManager.getInstance().disableAppResume()
        Log.d("hello", "Disable Ads Resume Setting")
    }

    private fun enableAdsResume() {
        if (ResumeAdsEntryRule.shouldEnableOpenResume()) {
            AppOpenManager.getInstance().enableAppResume()
            Log.d("hello", "Enable Ads Resume Setting")
        }
    }
}
