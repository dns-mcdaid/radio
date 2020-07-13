package dev.dennismcdaid.radio.ui.program.detail

import dev.dennismcdaid.radio.data.model.Program

sealed class ProgramDetailViewState {
    object Loading : ProgramDetailViewState()
    data class ProgramLoaded(val program: Program): ProgramDetailViewState()
    data class Error(val message: String?) : ProgramDetailViewState()
}