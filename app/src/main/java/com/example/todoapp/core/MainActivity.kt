package com.example.todoapp.core

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.example.todoapp.data.local.AppLocalStorage
import com.example.todoapp.presentaton.home.HomeScreen
import com.example.todoapp.utils.navigation.AppNavigatorHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: AppNavigatorHandler

    @Inject
    lateinit var appLocalStorage: AppLocalStorage


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            val useDarkIcons = true
            val window = (LocalContext.current as? Activity)?.window ?: return@setContent


            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars =
                useDarkIcons

            window.navigationBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(
                window,
                window.decorView
            ).isAppearanceLightNavigationBars =
                useDarkIcons

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Navigator(HomeScreen()) { navigator: Navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.navigation
                            .collect {
                                it(navigator)
                            }
                    }
                    CurrentScreen()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        appLocalStorage.time = System.currentTimeMillis()
    }
}