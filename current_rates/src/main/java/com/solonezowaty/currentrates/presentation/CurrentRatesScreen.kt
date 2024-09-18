package com.solonezowaty.currentrates.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.solonezowaty.core.Destinations
import com.solonezowaty.core.presentation.composables.ErrorRetryComponent
import com.solonezowaty.core.presentation.composables.LoadingProgressBar
import com.solonezowaty.core.presentation.composables.NbpRatesTopBar
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currentrates.R
import com.solonezowaty.currentrates.presentation.components.CurrentRatesList

@Composable
fun CurrentRatesScreen(
    navController: NavController,
    state: CurrentRatesViewState,
    onAction: (action: CurrentRatesAction) -> Unit
) {
    Scaffold(
        topBar = {
            NbpRatesTopBar(
                navController = navController,
                screenTitle = stringResource(R.string.current_rates_screen_title_text)
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when(state.currentRatesNetworkResponse) {
                is NetworkResponse.Error -> {
                    ErrorRetryComponent(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        onAction(CurrentRatesAction.Retry)
                    }
                }
                is NetworkResponse.Loading -> {
                    LoadingProgressBar(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is NetworkResponse.Success -> {
                    state.currentRatesNetworkResponse.data?.let { ratesList ->
                        CurrentRatesList(
                            ratesList = ratesList,
                            onDetailsClicked = { tableType, code ->
                                navController.navigate(
                                    Destinations.CurrencyDetails.createRoute(
                                        tableType,
                                        code
                                    )
                                )
                            },
                            onPageScrolled = {
                                onAction(CurrentRatesAction.LoadNextPage)
                            }
                        )
                    }?: ErrorRetryComponent(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        onAction(CurrentRatesAction.Retry)
                    }
                }
            }
        }
    }
}