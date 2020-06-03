package dev.dennismcdaid.radio.ui.main

import androidx.annotation.StringRes
import coil.api.load
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.databinding.ViewPlayerBinding

sealed class PlayerViewState {

    data class Active(
        val name: String,
        val description: String,
        val image: String,
        val playing: Boolean
    ) : PlayerViewState()

    object Loading : PlayerViewState() {
        @StringRes
        val message: Int = R.string.loading
    }

    data class Error(
        val description: String
    ) : PlayerViewState()
}

fun ViewPlayerBinding.bind(state: PlayerViewState) {
    when (state) {
        is PlayerViewState.Active -> {
            playButton.isEnabled = true
            val res = if (state.playing) R.drawable.ic_pause else R.drawable.ic_play
            playButton.setImageResource(res)
            showName.text = state.name
            showDesc.text = state.description
            presenterAvatar.load(state.image) {
                crossfade(true)
                placeholder(R.drawable.ic_account)
                error(R.drawable.ic_account)
            }
        }
        is PlayerViewState.Loading -> {
            showName.setText(PlayerViewState.Loading.message)
            playButton.isEnabled = false
        }
        is PlayerViewState.Error -> {
            playButton.isEnabled = true
            playButton.setImageResource(R.drawable.ic_retry)
        }
    }
}