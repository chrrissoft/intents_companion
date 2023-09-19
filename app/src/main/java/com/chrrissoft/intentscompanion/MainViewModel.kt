package com.chrrissoft.intentscompanion

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.chrrissoft.intentscompanion.ui.ScreenEvent
import com.chrrissoft.intentscompanion.ui.ScreenState
import com.chrrissoft.intentscompanion.ui.ScreenState.ReplayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ScreenState())
    val stateFlow = _state.asStateFlow()
    private val state get() = stateFlow.value
    private val replay get() = state.replayState
    private val roundTrip get() = state.roundTripState

    private val handler = EventHandler()

    fun onEvent(event: ScreenEvent) {
        handler.handleEvent(event)
    }


    inner class EventHandler {
        private val replyState get() = state.replayState
        private val roundTripState get() = state.roundTripState

        fun handleEvent(event: ScreenEvent) {
            when (event) {
                is ScreenEvent.OnChangePage -> handleEvent(event)
                is ScreenEvent.OnUpdatePendingIntent -> handleEvent(event)
                is ScreenEvent.OnUpdateStartIntent -> handleEvent(event)
            }
        }

        private fun handleEvent(event: ScreenEvent.OnChangePage) {
            updateState(page = event.page)
        }

        private fun handleEvent(event: ScreenEvent.OnUpdatePendingIntent) {
            logDebug(event.intent)
            updateRoundTripState(pendingIntent = event.intent)
        }

        private fun handleEvent(event: ScreenEvent.OnUpdateStartIntent) {
            updateRoundTripState(startIntent = event.intent)
            updateReplayState(intent = event.intent)
        }

        private fun updateReplayState(intent: Intent? = replyState.intent) {
            val replay = state.replayState.copy(intent = intent)
            updateState(replayState = replay)
        }

        private fun updateRoundTripState(
            pendingIntent: PendingIntent? = roundTripState.pendingIntent,
            startIntent: Intent? = roundTripState.startIntent,
        ) {
            val roundTrip = state.roundTripState.copy(
                pendingIntent = pendingIntent,
                startIntent = startIntent
            )
            updateState(roundTripState = roundTrip)
        }
    }


    private fun updateState(
        page: ScreenState.Page = state.page,
        replayState: ReplayState = state.replayState,
        roundTripState: ScreenState.RoundTripState = state.roundTripState,
    ) {
        logDebug("--- ${roundTripState.pendingIntent}")
        _state.update {
            it.copy(
                page = page,
                replayState = replayState,
                roundTripState = roundTripState
            )
        }
    }


    fun enableRoundTrip() {
        updateState(
            roundTripState = roundTrip.copy(enabled = true)
        )
    }

    fun disableRoundTrip() {
        updateState(
            roundTripState = roundTrip.copy(enabled = false)
        )
    }

    private companion object {
        private const val TAG = "MainViewModel"

        private fun logDebug(message: Any?) {
            Log.d(TAG, message.toString())
        }

        private fun logInfo(message: Any?) {
            Log.i(TAG, message.toString())
        }
    }
}
