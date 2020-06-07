package dev.dennismcdaid.radio.ui.util

import android.widget.ImageView
import coil.api.load
import coil.transform.CircleCropTransformation
import dev.dennismcdaid.radio.R
import dev.dennismcdaid.radio.data.model.emit.EmitProgram

fun ImageView.loadPresenter(program: EmitProgram, circleCrop: Boolean = false) = load(program.avatarUrl) {
    crossfade(true)
    placeholder(R.drawable.ic_account)
    if (circleCrop) transformations(CircleCropTransformation())
    error(R.drawable.ic_account)
}