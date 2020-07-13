package dev.dennismcdaid.radio.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.ui.Event
import dev.dennismcdaid.radio.ui.asLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

open class BaseViewModel : ViewModel() {
    protected val mainContext = Dispatchers.Default + viewModelScope.coroutineContext

    protected val snackbarEmitter = BroadcastChannel<String>(1)

    val snackbarEvent: LiveData<Event<String>> = snackbarEmitter.asLiveEvent(mainContext)
}