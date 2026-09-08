package com.anrstudio.template.ui.component.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast
import com.anrstudio.template.ui.bases.BaseDialog
import com.anrstudio.template.ui.bases.ext.click
import com.pegas.aura.aigirlfriend.soul.BuildConfig
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.databinding.DialogRateAppBinding


class DialogRateApp(
    context: Context,
    val onRatingHighScore: () -> Unit,
    val onRatingLowScore: () -> Unit
) : BaseDialog<DialogRateAppBinding>(context, R.style.BaseDialog) {

    override fun getLayoutDialog(): Int {
        return R.layout.dialog_rate_app
    }

    override fun initViews() {
        super.initViews()

        mBinding.simpleRatingBar.setOnRatingChangeListener { _, rating, _ ->
            if (rating > 0 && rating < 4) {
                mBinding.layoutFeedback.visibility = View.VISIBLE
                mBinding.tvContent.visibility = View.GONE
                mBinding.btnRateNow.text = context.getString(R.string.send_feedback)
            } else {
                mBinding.layoutFeedback.visibility = View.GONE
                mBinding.tvContent.visibility = View.VISIBLE
                mBinding.btnRateNow.text = context.getString(R.string.rate_now)
            }
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        mBinding.btnNotNow.click {
            dismiss()
        }

        mBinding.btnRateNow.click {
            val rating = mBinding.simpleRatingBar.rating
            if (rating > 0) {
                if (rating >= 4) {
                    dismiss()
                    onRatingHighScore.invoke()
                } else {
                    val reason = mBinding.edtReason.text.toString().trim()
                    val description = mBinding.edtDescription.text.toString().trim()

                    sendFeedbackEmail(rating, reason, description)

                    dismiss()
                    onRatingLowScore.invoke()
                }
            }
        }
    }

    private fun sendFeedbackEmail(rating: Float, reason: String, description: String) {
        val email = BuildConfig.email_feedback
        val subject = "Feedback for AI Girlfriend (Rating: ${rating.toInt()} stars)"
        val deviceModel = Build.MODEL
        val androidVersion = Build.VERSION.RELEASE
        val appVersion = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            "Unknown"
        }

        val body = """
            Rating: ${rating.toInt()} / 5 stars
            Reason: ${if (reason.isEmpty()) "N/A" else reason}
            
            Description:
            ${if (description.isEmpty()) "N/A" else description}
            
            --- Device Info ---
            App Version: $appVersion
            Device Model: $deviceModel
            Android Version: $androidVersion
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        val gmailIntent = Intent(intent).apply {
            `package` = "com.google.android.gm"
        }

        try {
            context.startActivity(gmailIntent)
        } catch (e: Exception) {
            try {
                context.startActivity(Intent.createChooser(intent, "Send Email via:"))
            } catch (ex: Exception) {
                Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}