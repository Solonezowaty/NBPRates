package com.solonezowaty.currencydetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRateDetailsUseCase: com.solonezowaty.currencydetails.domain.usecases.GetRateDetailsUseCase
) : ViewModel() {

    private val tableType = checkNotNull(savedStateHandle.get<String?>("tableType"))
    private val code = checkNotNull(savedStateHandle.get<String?>("code"))

    private val _state = MutableStateFlow(CurrencyDetailsViewState())
    val state = _state.asStateFlow()

    init {
        getCurrencyDetails()
    }

    private fun getCurrencyDetails() {
        val endDateInstant = Instant.now()
        val startDateInstant = endDateInstant.minus(14, ChronoUnit.DAYS)


        viewModelScope.launch {
            _state.value = _state.value.copy(
                currencyDetails = com.solonezowaty.core.utils.NetworkResponse.Loading()
            )
            getRateDetailsUseCase.getRatesDetails(
                tableType = tableType,
                currencyCode = code,
                startDate = startDateInstant,
                endDate = endDateInstant,
            ).onRight { currencyDetails ->
                _state.update {
                    it.copy(
                        currencyDetails = com.solonezowaty.core.utils.NetworkResponse.Success(currencyDetails)
                    )
                }
            }.onLeft { throwable ->
                _state.value =
                    _state.value.copy(
                        currencyDetails = com.solonezowaty.core.utils.NetworkResponse.Error(throwable.message ?: "")
                    )
            }
        }
    }
}