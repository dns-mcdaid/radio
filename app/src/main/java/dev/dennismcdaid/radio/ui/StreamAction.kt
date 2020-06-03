package dev.dennismcdaid.radio.ui

sealed class StreamAction {
    object Stop : StreamAction()
    object Error: StreamAction()
    data class Start(val url: String) : StreamAction()
}