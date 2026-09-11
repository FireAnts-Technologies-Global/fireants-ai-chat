package com.pegas.yuki.virtual.chat.ui.bases.compose.component

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.facebook.shimmer.ShimmerFrameLayout
import com.fireants.adsdk.R
import com.fireants.adsdk.ads.FireAntsAdSdk
import com.fireants.adsdk.billing.AppPurchase
import com.fireants.adsdk.funtion.AdCallback
import com.google.android.gms.ads.LoadAdError
import kotlinx.coroutines.delay

@Composable
fun BannerAdView(
    adUnitId: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val context = LocalContext.current
    var isPurchased by remember { mutableStateOf(AppPurchase.getInstance().isPurchased(context)) }
    var rootView by remember { mutableStateOf<View?>(null) }
    var bannerContainer by remember { mutableStateOf<FrameLayout?>(null) }
    var shimmerView by remember { mutableStateOf<ShimmerFrameLayout?>(null) }

    val lifecycleOwner = context as? LifecycleOwner
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val purchased = AppPurchase.getInstance().isPurchased(context)
                if (purchased) {
                    isPurchased = true
                    try {
                        shimmerView?.stopShimmer()
                        shimmerView?.visibility = View.GONE
                        bannerContainer?.removeAllViews()
                        bannerContainer?.visibility = View.GONE
                        rootView?.visibility = View.GONE
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        lifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose {
            lifecycleOwner?.lifecycle?.removeObserver(observer)
            try {
                shimmerView?.stopShimmer()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                bannerContainer?.removeAllViews()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (!isEnabled || isPurchased) {
        return
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        factory = { ctx ->
            val root = LayoutInflater.from(ctx).inflate(
                R.layout.layout_banner_control,
                null,
                false
            )

            val banner = root.findViewById<FrameLayout>(R.id.banner_container)
            val shimmer = root.findViewById<ShimmerFrameLayout>(R.id.shimmer_container_banner)

            banner?.removeAllViews()
            banner?.visibility = View.GONE

            shimmer?.visibility = View.VISIBLE
            shimmer?.startShimmer()

            rootView = root
            bannerContainer = banner
            shimmerView = shimmer

            root
        },
        update = { view ->
            if (isPurchased || AppPurchase.getInstance().isPurchased(context)) {
                view.visibility = View.GONE
                bannerContainer?.removeAllViews()
                shimmerView?.stopShimmer()
                shimmerView?.visibility = View.GONE
            }
        }
    )

    LaunchedEffect(adUnitId, rootView) {

        val root = rootView
        val container = bannerContainer
        val shimmer = shimmerView

        if (root == null || container == null) {
            return@LaunchedEffect
        }

        if (context !is AppCompatActivity) {
            return@LaunchedEffect
        }

        container.removeAllViews()
        container.visibility = View.GONE

        shimmer?.visibility = View.VISIBLE
        shimmer?.startShimmer()
        delay(200)

        try {
            FireAntsAdSdk.getInstance().loadBannerFragment(
                context,
                adUnitId,
                root,
                object : AdCallback() {
                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        shimmer?.stopShimmer()
                        shimmer?.visibility = View.GONE
                        container.visibility = View.VISIBLE

                    }

                    override fun onAdFailedToLoad(adError: LoadAdError?) {
                        super.onAdFailedToLoad(adError)
                        shimmer?.stopShimmer()
                        shimmer?.visibility = View.GONE
                        container.visibility = View.GONE

                    }

                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            shimmer?.stopShimmer()
            shimmer?.visibility = View.GONE
            container.visibility = View.GONE
        }
    }
}