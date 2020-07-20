package dev.dennismcdaid.radio.service

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import dagger.android.DaggerBroadcastReceiver
import timber.log.Timber

class DownloadBroadcastReceiver : DaggerBroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
            Timber.d("EUREKA!")
        }
    }
}