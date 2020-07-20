package dev.dennismcdaid.radio.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import dagger.android.DaggerService
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.ui.main.MainActivity
import dev.dennismcdaid.radio.util.asHttps
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class AudioPlayerService : DaggerService() {

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private val player = MediaPlayer().apply {
        setOnPreparedListener { start() }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(SERVICE_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra(STREAM_URL_KEY)
            ?: return super.onStartCommand(intent, flags, startId)

        if (!player.isPlaying) {
            GlobalScope.launch {
                followRedirects(url)
                    .catch { e ->
                        Timber.e(e)
                        emit(url)
                    }
                    .map { it.asHttps() }
                    .collect {
                        player.setDataSource(applicationContext, it.toUri())
                        player.isLooping = true
                        player.prepareAsync()
                    }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        player.stop()
        super.onDestroy()
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.stream_audio),
            NotificationManager.IMPORTANCE_LOW
        )
        channel.setShowBadge(false)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        createChannel()
        val notificationIntent = Intent(this, MainActivity::class.java).apply {
            flags = FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP
        }
        val intent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return Notification.Builder(this, CHANNEL_ID)
            .setColor(ContextCompat.getColor(this, R.color.pbs_red))
            .setContentIntent(intent)
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .setContentTitle(getString(R.string.now_playing))
            .build()
    }

    private fun followRedirects(original: String): Flow<String> {
        val request = Request.Builder()
            .url(original)
            .head()
            .build()

        return callbackFlow {
            val callback = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    sendBlocking(original)
                    close()
                }
                override fun onResponse(call: Call, response: Response) {
                    sendBlocking(response.request.url.toString())
                    close()
                }
            }
            val call = okHttpClient.newCall(request)
            call.enqueue(callback)
            awaitClose { call.cancel() }
        }
    }


    companion object {
        const val STREAM_URL_KEY = "STREAM_URL"
        const val LOCAL_URL = "LOCAL_URL"
        private const val CHANNEL_ID = "audio_playback_channel"
        private const val SERVICE_ID = 1067

        private const val BUFFER_SEGMENT_SIZE = 64 * 1024
        private const val BUFFER_SEGMENT_COUNT = 256
    }
}