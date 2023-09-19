package com.chrrissoft.intentscompanion

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.chrrissoft.intentscompanion.Util.getPendingIntent
import com.chrrissoft.intentscompanion.ui.Screen
import com.chrrissoft.intentscompanion.ui.ScreenEvent
import com.chrrissoft.intentscompanion.ui.ScreenState.Page.ROUND_TRIP
import com.chrrissoft.intentscompanion.ui.components.App
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.stateFlow.collectAsState()
            App {
                Screen(state = state, onEvent = { viewModel.onEvent(it) })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent.getPendingIntent().let {
            if (it!=null) {
                viewModel.onEvent(ScreenEvent.OnChangePage(ROUND_TRIP))
                viewModel.enableRoundTrip()
                viewModel.onEvent(ScreenEvent.OnUpdatePendingIntent(it))
            } else viewModel.disableRoundTrip()
        }

        intent.let {
            if (it.action == "android.intent.action.MAIN") return
            viewModel.onEvent(ScreenEvent.OnUpdateStartIntent(it))
        }
    }

    companion object {
//        private const val TAG = "MainActivity"

        @SuppressLint("ComposableNaming")
        @Composable
        fun setBarsColors(
            status: Color = MaterialTheme.colorScheme.primaryContainer,
            bottom: Color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setStatusBarColor(status, useDarkIcons)
                systemUiController.setNavigationBarColor(bottom)
                onDispose {}
            }
        }
    }
}
