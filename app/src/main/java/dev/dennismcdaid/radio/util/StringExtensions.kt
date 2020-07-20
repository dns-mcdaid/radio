package dev.dennismcdaid.radio.util

fun String.asHttps(): String {
    return if (contains(HTTPS)) this else this.replace(HTTP, HTTPS)
}

private const val HTTPS = "https"
private const val HTTP = "http"