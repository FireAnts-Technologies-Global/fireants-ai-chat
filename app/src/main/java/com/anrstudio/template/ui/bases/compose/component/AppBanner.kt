package com.anrstudio.template.ui.bases.compose.component

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
import com.anrstudio.ads.R
import com.anrstudio.ads.ads.ANRAdSdk
import com.anrstudio.ads.billing.AppPurchase
import com.anrstudio.ads.funtion.AdCallback
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.LoadAdError
import kotlinx.coroutines.delay


@Composable
fun BannerAdView(
    adUnitId: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val context = LocalContext.current
    var rootView by remember { mutableStateOf<View?>(null) }
    var bannerContainer by remember { mutableStateOf<FrameLayout?>(null) }
    var shimmerView by remember { mutableStateOf<ShimmerFrameLayout?>(null) }
    val instanceId = remember { System.currentTimeMillis().toString() }


    if (!isEnabled || AppPurchase.getInstance().isPurchased(context)) {
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
            ANRAdSdk.getInstance().loadBannerFragment(
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

    DisposableEffect(instanceId) {

        onDispose {

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

}