package com.chrrissoft.intentscompanion.ui

import android.content.ComponentName
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chrrissoft.intentscompanion.Constants.CUSTOM_ACTION_BUILD_SEND
import com.chrrissoft.intentscompanion.ui.ScreenState.Actions.mainAppSendActions
import com.chrrissoft.intentscompanion.ui.components.IntentReader

@Composable
fun ReplayPage(
    state: ScreenState.ReplayState,
) {
    if (state.intent!=null) {
        IntentReader(
            title = "Start Intent Reader",
            intent = state.intent,
            actions = mainAppSendActions
        )
    } else {
        val ctx = LocalContext.current
        Box(
            modifier = Modifier
                .clickable {
                    Intent(CUSTOM_ACTION_BUILD_SEND).apply {
                        component = ComponentName(
                            "com.chrrissoft.intents",
                            "com.chrrissoft.intents.MainActivity"
                        )
                        ctx.startActivity(this)
                    }
                }
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Tap to start main app and build an intent for this app")
        }
    }
}
