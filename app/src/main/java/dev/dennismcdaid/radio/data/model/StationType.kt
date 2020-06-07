package dev.dennismcdaid.radio.data.model

enum class StationType(
    val emitCallSign: String,
    val airnetCallSign: String
) {

    PBS("pbs", "3pbs");
}