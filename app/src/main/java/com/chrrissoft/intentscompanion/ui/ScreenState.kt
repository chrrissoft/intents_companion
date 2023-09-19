package com.chrrissoft.intentscompanion.ui

import android.app.PendingIntent
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.ui.graphics.vector.ImageVector
import com.chrrissoft.intentscompanion.Constants.CUSTOM_ACTION_ONE
import com.chrrissoft.intentscompanion.Constants.CUSTOM_ACTION_TWO
import com.chrrissoft.intentscompanion.Constants.MAIN_APP_CUSTOM_ACTION_ONE
import com.chrrissoft.intentscompanion.Constants.MAIN_APP_CUSTOM_ACTION_TWO
import com.chrrissoft.intentscompanion.Constants.MAIN_APP_CUSTOM_SEND_ACTION_ONE
import com.chrrissoft.intentscompanion.Constants.MAIN_APP_CUSTOM_SEND_ACTION_TWO

data class ScreenState(
    val page: Page = Page.REPLAY,
    val replayState: ReplayState = ReplayState(),
    val roundTripState: RoundTripState = RoundTripState()
) {
    enum class Page(
        val label: String, val icon: ImageVector
    ) {
        REPLAY(
            label = "Replay",
            icon = Icons.Rounded.Replay
        ),
        ROUND_TRIP(
            label = "Round trip",
            icon = Icons.Rounded.Sync
        );

        companion object {
            val pages = listOf(REPLAY, ROUND_TRIP)
        }
    }

    data class ReplayState(val intent: Intent? = null)

    data class RoundTripState(
        val enabled: Boolean = false,
        val startIntent: Intent? = null,
        val pendingIntent: PendingIntent? = null,
    )

    object Actions {
        val actions = listOf(CUSTOM_ACTION_ONE, CUSTOM_ACTION_TWO)
        val mainAppActions = listOf(MAIN_APP_CUSTOM_ACTION_ONE, MAIN_APP_CUSTOM_ACTION_TWO)
        val mainAppSendActions = listOf(MAIN_APP_CUSTOM_SEND_ACTION_ONE, MAIN_APP_CUSTOM_SEND_ACTION_TWO)
    }
}
