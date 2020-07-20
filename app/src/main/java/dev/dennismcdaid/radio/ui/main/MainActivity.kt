package dev.dennismcdaid.radio.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
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
    private lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container)
        binding.navigationView.setupWithNavController(navController)

        // Setup Toolbar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.schedule, R.id.programs))
        binding.toolbarLayout.setupWithNavController(binding.toolbar, navController, appBarConfiguration)


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
                    val intent = Intent(this, AudioPlayerService::class.java).apply {
                        putExtra(AudioPlayerService.STREAM_URL_KEY, action.url)
                    }
                    startForegroundService(intent)
                }
            }
        })

        binding.nowPlaying.root.setOnClickListener {
            Timber.d("Clicked!")
        }
    }

    fun setTitle(title: String) {
        binding.toolbarLayout.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}