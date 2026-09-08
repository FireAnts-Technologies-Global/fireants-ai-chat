package com.anrstudio.template.ui.bases.compose.activity

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.anrstudio.template.app.AppConstants
import com.anrstudio.template.data.pref.AppSharedPref
import com.anrstudio.template.data.pref.AppSharedPreferencesApp
import com.anrstudio.template.utils.ANRTrackingHelper
import com.anrstudio.template.utils.Routes
import java.util.Locale
import javax.inject.Inject

abstract class BaseComposeActivity : AppCompatActivity() {

    @Inject
    lateinit var appSharedPref: AppSharedPref

    protected open val statusBarStyle: SystemBarStyle = SystemBarStyle.auto(
        Color.TRANSPARENT,
        Color.TRANSPARENT
    )

    protected open val navigationBarStyle: SystemBarStyle = SystemBarStyle.auto(
        Color.TRANSPARENT,
        Color.TRANSPARENT
    )

    override fun attachBaseContext(base: Context) {
        val currentLanguage = AppSharedPreferencesApp(base).languageCode
        val locale = Locale.Builder().setLanguage(currentLanguage).build()
        Locale.setDefault(locale)
        super.attachBaseContext(updateResourcesLocale(base, locale))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle
        )
        super.onCreate(savedInstanceState)
        hideNavigationBar()
        logScreenTracking()
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                Content()
            }
        }
    }

    @Composable
    protected abstract fun Content()

    private fun logScreenTracking() {
        ANRTrackingHelper.addScreenTrack(this::class.java.simpleName)
        intent.getStringExtra(AppConstants.KEY_TRACKING_SCREEN_FROM)?.let { from ->
            Routes.addTrackingMoveScreen(from, this::class.java.simpleName)
        }
    }

    private fun updateResourcesLocale(context: Context, locale: Locale): Context {
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    private fun hideNavigationBar() {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
