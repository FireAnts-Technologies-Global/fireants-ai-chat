package com.pegas.yuki.virtual.chat.ui.bases.ext

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager


fun isNetwork(activity: Activity): Boolean {
    val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}