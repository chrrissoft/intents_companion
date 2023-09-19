package com.chrrissoft.intentscompanion.ui

import android.app.PendingIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import com.chrrissoft.intentscompanion.Util.component
import com.chrrissoft.intentscompanion.Util.isImmutableTextCompat
import com.chrrissoft.intentscompanion.ui.ScreenState.Actions.mainAppActions
import com.chrrissoft.intentscompanion.ui.ScreenState.RoundTripState
import com.chrrissoft.intentscompanion.ui.components.Container
import com.chrrissoft.intentscompanion.ui.components.IntentReader


@Composable
fun RoundTripPage(
    state: RoundTripState,
) {
    if (!state.enabled) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No action was performed",
                style = typography.labelLarge.copy(color = colorScheme.onPrimary),
                fontWeight = FontWeight.Medium
            )
        }
        return
    }

    PendingIntentReader(state = state.pendingIntent)
    IntentReader(
        title = "Start Intent Reader",
        state.startIntent,
        actions = mainAppActions
    )
}


@Composable
private fun PendingIntentReader(
    state: PendingIntent?,
) {
    Container(
        title = "Pending Intent Reader"
    ) {
        if (state==null) {
            Text(
                text = "Pending Intent is null",
                style = typography.titleMedium.copy(textAlign = Center),
                modifier = Modifier.fillMaxWidth()
            )
            return@Container
        }

        Text(
            style = typography.labelMedium,
            text = "Creator uid: ${state.creatorUid}",
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        )
        Text(
            style = typography.labelMedium,
            text = "Creator package: ${state.creatorPackage}",
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        )
        Text(
            style = typography.labelMedium,
            text = "Is immutable: ${state.isImmutableTextCompat}",
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        )
        Text(
            style = typography.labelMedium,
            text = "Component: ${state.component}",
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        )
    }
}
