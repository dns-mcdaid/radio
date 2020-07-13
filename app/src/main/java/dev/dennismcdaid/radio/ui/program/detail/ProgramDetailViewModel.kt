package dev.dennismcdaid.radio.ui.program.detail

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.dennismcdaid.radio.data.StationRepository
import dev.dennismcdaid.radio.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProgramDetailViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : BaseViewModel() {

    private val viewState = MutableStateFlow<ProgramDetailViewState>(ProgramDetailViewState.Loading)

    val viewStateLiveData = viewState.asLiveData(mainContext)

    fun setSlug(slug: String) {
        viewModelScope.launch {
            stationRepository.getProgram(slug)
                .catch { e ->
                    viewState.value = ProgramDetailViewState.Error(e.localizedMessage)
                }
                .collect {
                    viewState.value = ProgramDetailViewState.ProgramLoaded(it)
                }
        }
    }
}