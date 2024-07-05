package com.example.newsapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.newsapp.BuildConfig
import com.example.newsapp.navigation.NavGraph
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            // We want to display splash screen until we get value of splash condition, so we need to
            // use setKeepOnScreenCondition
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }
        enableEdgeToEdge()
        setContent {
            NewsAppTheme(dynamicColor = false) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(startDestination = viewModel.startDestination.value)
                }

            }
        }
    }
}
