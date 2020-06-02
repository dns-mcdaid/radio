package dev.dennismcdaid.radio.ui.util

import android.widget.ImageView
import coil.api.load
import coil.transform.CircleCropTransformation
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.data.model.Episode
import dev.dennismcdaid.radio.data.model.Program

fun ImageView.loadPresenter(program: Program, circleCrop: Boolean = false) = load(program.imageUrl) {
    crossfade(true)
    placeholder(R.drawable.ic_account)
    if (circleCrop) transformations(CircleCropTransformation())
    error(R.drawable.ic_account)
}