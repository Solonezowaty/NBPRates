package com.solonezowaty.currentrates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currentrates.domain.model.RateItem
import com.solonezowaty.currentrates.domain.usecase.GetCurrentRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentRatesViewModel @Inject constructor(
    private val getCurrentRatesUseCase: GetCurrentRatesUseCase
) : ViewModel() {

    private var rateItemsList: List<RateItem> = listOf()
    private var currentPage: Int = 0
    private val pageSize: Int = 20

    private val _state = MutableStateFlow(CurrentRatesViewState())
    val state: StateFlow<CurrentRatesViewState> = _state.asStateFlow()

    init {
        getNbpRatesList()
    }

    fun onAction(action: CurrentRatesAction) {
        when (action) {
            CurrentRatesAction.Retry -> getNbpRatesList()
            CurrentRatesAction.LoadNextPage -> loadNextPage()
        }
    }

    private fun getNbpRatesList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(currentRatesNetworkResponse = NetworkResponse.Loading())
            getCurrentRatesUseCase.getCurrentRates()
                .onRight { ratesList ->
                    rateItemsList = ratesList
                    loadNextPage()
                }.onLeft { error: Throwable ->
                    _state.value =
                        _state.value.copy(currentRatesNetworkResponse = NetworkResponse.Error(error.message))
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
                    currentRatesNetworkResponse = NetworkResponse.Success(
                        _state.value.currentRatesNetworkResponse.data.orEmpty() + nextPageItems
                    )
                )
            }
            currentPage++
        }
    }
}