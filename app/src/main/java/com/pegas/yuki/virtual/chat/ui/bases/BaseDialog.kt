package com.pegas.yuki.virtual.chat.ui.bases

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.data.pref.AppSharedPref
import com.pegas.yuki.virtual.chat.data.pref.BaseDialogEntryPoint
import dagger.hilt.EntryPoints
import java.util.Locale

abstract class BaseDialog<VB : ViewDataBinding>(
    context: Context,
    themeResId: Int = R.style.ThemeDialog
) :
    Dialog(context, themeResId) {
    lateinit var mBinding: VB

    private val appSharedPref: AppSharedPref by lazy {
        EntryPoints.get(
            context.applicationContext,
            BaseDialogEntryPoint::class.java
        ).appSharedPref()
    }


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        createContentView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLocal()
        initViews()
        onResizeViews()
        onClickViews()
    }

    private fun createContentView() {
        val layoutView = getLayoutDialog()
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutView, null, false)
        setContentView(mBinding.root)
    }

    abstract fun getLayoutDialog(): Int

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}


    private fun setLocal() {
        val languageCode = appSharedPref.languageCode
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
