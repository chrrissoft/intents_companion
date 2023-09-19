package com.chrrissoft.intentscompanion.ui

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.chrrissoft.intentscompanion.MainViewModel
import org.intellij.lang.annotations.Identifier

sealed interface ScreenEvent {
    data class OnChangePage(val page: ScreenState.Page) : ScreenEvent

    data class OnUpdatePendingIntent(val intent: PendingIntent) : ScreenEvent

    data class OnUpdateStartIntent(val intent: Intent) : ScreenEvent
}
