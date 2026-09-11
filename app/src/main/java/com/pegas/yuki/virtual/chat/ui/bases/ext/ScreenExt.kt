package com.pegas.yuki.virtual.chat.ui.bases.ext

import android.content.Context
import android.util.DisplayMetrics

fun Number.dpToPx(context: Context): Float {
    return this.toFloat() * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


