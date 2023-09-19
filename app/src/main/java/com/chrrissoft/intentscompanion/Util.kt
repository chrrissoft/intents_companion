package com.chrrissoft.intentscompanion

import android.R.drawable.sym_def_app_icon
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.S
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import java.util.ArrayList
import kotlin.random.Random

object Util {
    private fun componentStartedNotification(ctx: Context, title: String): Notification {
        return NotificationCompat.Builder((ctx), IntentCompanionApp.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(sym_def_app_icon)
            .build()
    }

    fun NotificationManager.notifyComponentStarted(ctx: Context, title: String) {
        notify(Random.nextInt(), componentStartedNotification(ctx, title))
    }

    fun Intent.getPendingIntent(): PendingIntent? {
        return getParcelableExtraCompat(
            key = Constants.PENDING_INTENT_EXTRA_KEY,
            clazz = PendingIntent::class.java
        )
    }

    private fun<T : Parcelable> Intent.getParcelableExtraCompat(key: String, clazz: Class<T>): T? {
        return if (SDK_INT >= TIRAMISU) getParcelableExtra(key, clazz)
        else getParcelableExtra(key)
    }

    fun Intent.getBundleData(): List<Pair<String, String>> {
        val map = mutableMapOf<String, String>()
        getStringArrayListExtra(BUNDLE_DATA_KEY_LIST)?.forEach { key ->
            val value = getStringExtra(key) ?: ""
            map[key] = value
        }
        return map.toList()
    }

    fun Intent.setBundleDataKeyList(keys: List<String>): Intent {
        val array = ArrayList<String>().apply {
            keys.forEach { this.add(it) }
        }
        putStringArrayListExtra(BUNDLE_DATA_KEY_LIST, array)
        return this
    }

    fun String.toUiAction() : String {
        return takeLastWhile { it.toString() != "." }
    }

    val PendingIntent.isImmutableTextCompat : String get() = run {
        return if (SDK_INT >= S) isImmutable.toString()
        else "Android Version Not Supported"
    }

    val PendingIntent.isImmutableCompat : Boolean get() = run {
        return if (SDK_INT >= S) this.isImmutable
        else true
    }

    val PendingIntent.component : String get() = run {
        if (SDK_INT >= S) {
            if (isActivity) "activity"
            else if (isService) "service"
            else if (isBroadcast) "broadcast"
            else throw IllegalStateException("Unknown Component")
        } else "Android Version Not Supported"
    }

    private const val BUNDLE_DATA_KEY_LIST = "bundle_data_key_list"
}
