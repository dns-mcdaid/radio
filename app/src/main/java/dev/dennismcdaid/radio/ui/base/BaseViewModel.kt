package dev.dennismcdaid.radio.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

open class BaseViewModel : ViewModel() {
    protected val mainContext = Dispatchers.Default + viewModelScope.coroutineContext
}