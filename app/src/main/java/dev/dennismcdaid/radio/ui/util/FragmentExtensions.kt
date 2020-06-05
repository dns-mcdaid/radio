package dev.dennismcdaid.radio.ui.util

import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val context = context ?: return
    val currentFocus = activity?.currentFocus ?: return
    context.getSystemService<InputMethodManager>()
        ?.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}