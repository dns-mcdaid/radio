package dev.dennismcdaid.radio.ui.main

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.ActivityMainBinding
import dev.dennismcdaid.radio.service.AudioPlayerService
import dev.dennismcdaid.radio.ui.EventObserver
import dev.dennismcdaid.radio.ui.StreamAction
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container)

        // TODO: Setup ActionBar

        binding.navigationView.setupWithNavController(navController)

        binding.nowPlaying.playButton.setOnClickListener {
            viewModel.onPlayClicked()
        }

        viewModel.playerState.observe(this) {
            binding.nowPlaying.bind(it)
        }

        viewModel.streamAction.observe(this, EventObserver { action ->
            when (action) {
                StreamAction.Stop -> {
                    stopService(Intent(this, AudioPlayerService::class.java))
                }
                StreamAction.Error -> {

                }
                is StreamAction.Start -> {
                    Timber.d("LAUNCHING")
                    val intent = Intent(this, AudioPlayerService::class.java).apply {
                        putExtra(AudioPlayerService.STREAM_URL_KEY, action.url)
                    }
                    startForegroundService(intent)
                }
            }
        })
    }
}