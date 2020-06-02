package dev.dennismcdaid.radio.service

import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import dagger.android.DaggerService

class AudioPlayerService : DaggerService() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val STREAM_URL_KEY = "STREAM_URL"
    }
}