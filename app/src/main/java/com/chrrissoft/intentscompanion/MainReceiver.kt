package com.chrrissoft.intentscompanion

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.chrrissoft.intentscompanion.Util.notifyComponentStarted
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        notificationManager.notifyComponentStarted(ctx = context, title = "MainReceiver was called - Companion")
    }
}
