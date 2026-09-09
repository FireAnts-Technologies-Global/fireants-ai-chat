package com.pegas.aura.aigirlfriend.soul.ui.component.dialog

import android.content.Context
import android.view.View
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.databinding.DialogRedeemPromoCodeBinding
import com.pegas.aura.aigirlfriend.soul.ui.bases.BaseDialog
import com.pegas.aura.aigirlfriend.soul.ui.bases.ext.click

class DialogRedeemPromoCode(
    context: Context,
    private val onRedeemSubmitted: (code: String, dialog: DialogRedeemPromoCode) -> Unit
) : BaseDialog<DialogRedeemPromoCodeBinding>(context, R.style.BaseDialog) {

    override fun getLayoutDialog(): Int = R.layout.dialog_redeem_promo_code

    override fun onClickViews() {
        super.onClickViews()

        mBinding.btnCancel.click {
            dismiss()
        }

        mBinding.btnRedeem.click {
            val code = mBinding.edtCode.text?.toString()?.trim().orEmpty()
            if (code.isBlank()) {
                showError(context.getString(R.string.promo_code_empty_error))
                return@click
            }
            hideError()
            onRedeemSubmitted(code, this)
        }
    }

    fun setLoading(isLoading: Boolean) {
        mBinding.btnRedeem.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        mBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        mBinding.edtCode.isEnabled = !isLoading
        mBinding.btnCancel.isEnabled = !isLoading
        setCancelable(!isLoading)
        setCanceledOnTouchOutside(!isLoading)
    }

    fun showError(message: String) {
        mBinding.tvError.text = message
        mBinding.tvError.visibility = View.VISIBLE
    }

    fun hideError() {
        mBinding.tvError.visibility = View.GONE
    }
}
