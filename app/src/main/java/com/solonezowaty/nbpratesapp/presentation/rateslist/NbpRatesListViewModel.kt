package com.solonezowaty.nbpratesapp.presentation.rateslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solonezowaty.nbpratesapp.domain.NbpRatesTableRepository
import com.solonezowaty.nbpratesapp.domain.model.RateItem
import com.solonezowaty.nbpratesapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NbpRatesListViewModel @Inject constructor(
    private val nbpRatesTableRepository: NbpRatesTableRepository
) : ViewModel() {

    private var rateItemsList: List<RateItem> = listOf()
    private var currentPage: Int = 0
    private val pageSize: Int = 20

    private val _state = MutableStateFlow(NbpRatesListViewState())
    val state: StateFlow<NbpRatesListViewState> = _state.asStateFlow()

    init {
        getNbpRatesList()
    }

    fun onAction(action: NbpRatesListAction) {
        when(action) {
            NbpRatesListAction.Retry -> getNbpRatesList()
            NbpRatesListAction.LoadNextPage -> loadNextPage()
        }
    }

    private fun getNbpRatesList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(ratesListNetworkResponse = NetworkResponse.Loading())
            nbpRatesTableRepository.getCurrentRates()
                .onRight { ratesList ->
                    rateItemsList = ratesList
                    loadNextPage()
                }.onLeft { error ->
                    _state.value =
                        _state.value.copy(ratesListNetworkResponse = NetworkResponse.Error(error))
                }
        }
    }

    /*
        I was asked to handle a large amount of data for display,
        but the API on which the application is based does not support pagination.
        Therefore, I implemented a sample mechanism on which pagination could work.
     */
    private fun loadNextPage() {
        val startIndex = currentPage * pageSize
        val endIndex = (startIndex + pageSize).coerceAtMost(rateItemsList.size)

        if (startIndex < rateItemsList.size) {
            val nextPageItems = rateItemsList.subList(startIndex, endIndex)
            _state.update {
                it.copy(
                    ratesListNetworkResponse = NetworkResponse.Success(
                        _state.value.ratesListNetworkResponse.data.orEmpty() + nextPageItems
                    )
                )
            }
            currentPage++
        }
    }
}