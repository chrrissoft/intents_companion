package com.chrrissoft.intentscompanion.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.intentscompanion.ui.theme.IntentsCompanionTheme

@Composable
fun App(app: @Composable () -> Unit) {
    IntentsCompanionTheme {
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) { app() }
    }
}
