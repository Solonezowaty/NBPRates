package com.solonezowaty.currencydetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solonezowaty.core.utils.Constants
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currencydetails.domain.usecases.GetCurrencyDetailsUseCase
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
    private val getCurrencyDetailsUseCase: GetCurrencyDetailsUseCase
) : ViewModel() {

    private val tableType = checkNotNull(savedStateHandle.get<String?>("tableType"))
    private val code = checkNotNull(savedStateHandle.get<String?>("code"))

    private val _state = MutableStateFlow(CurrencyDetailsViewState())
    val state = _state.asStateFlow()

    init {
        getCurrencyDetails()
    }

    fun onAction(action: CurrencyDetailsAction) {
        when (action) {
            CurrencyDetailsAction.Retry -> getCurrencyDetails()
        }
    }

    private fun getCurrencyDetails() {
        val endDateInstant = Instant.now()
        val startDateInstant = endDateInstant.minus(Constants.NUMBER_OF_DAYS_FOR_HISTORY, ChronoUnit.DAYS)


        viewModelScope.launch {
            _state.value = _state.value.copy(
                currencyDetailsNetworkResponse = NetworkResponse.Loading()
            )
            getCurrencyDetailsUseCase.getCurrencyDetails(
                tableType = tableType,
                currencyCode = code,
                startDate = startDateInstant,
                endDate = endDateInstant,
            ).onRight { currencyDetails ->
                _state.update {
                    it.copy(
                        currencyDetailsNetworkResponse = NetworkResponse.Success(currencyDetails)
                    )
                }
            }.onLeft { throwable ->
                _state.value =
                    _state.value.copy(
                        currencyDetailsNetworkResponse = NetworkResponse.Error(throwable.message ?: "")
                    )
            }
        }
    }
}