package com.pegas.yuki.virtual.chat.ui.component.dialog

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.databinding.DialogLoadingBinding
import com.pegas.yuki.virtual.chat.ui.bases.BaseDialog


class DialogLoading(context: Context) : BaseDialog<DialogLoadingBinding>(
    context,
    R.style.BaseDialog
) {

    private val handler = Handler(Looper.getMainLooper())
    private var dotCount = 0

    private val animateRunnable = object : Runnable {
        override fun run() {
            dotCount = (dotCount + 1) % 4
            val dots = ".".repeat(dotCount)
            mBinding.loading.text = "${context.getString(R.string.loading_label)}$dots"
            handler.postDelayed(this, 500)
        }
    }

    override fun getLayoutDialog(): Int {
        return R.layout.dialog_loading
    }

    override fun initViews() {
        super.initViews()
        setCancelable(false)
        handler.post(animateRunnable)
    }

    override fun dismiss() {
        handler.removeCallbacks(animateRunnable)
        super.dismiss()
    }
}