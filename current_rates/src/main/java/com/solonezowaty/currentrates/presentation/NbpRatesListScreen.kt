package com.solonezowaty.currentrates.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.solonezowaty.core.utils.NetworkResponse

@Composable
fun NbpRatesListScreen(
    navController: NavController,
    state: NbpRatesListViewState,
    onAction: (action: NbpRatesListAction) -> Unit
) {
    Scaffold(
        topBar = {
//            NbpRatesTopBar(
//                navController = navController,
//                screenTitle = ""//stringResource(R.string.current_rates_screen_title_text)
//            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when(state.ratesListNetworkResponse) {
                is NetworkResponse.Error -> {
//                    ErrorRetryComponent(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        onAction(NbpRatesListAction.Retry)
//                    }
                }
                is NetworkResponse.Loading -> {
//                    LoadingProgressBar(
//                        modifier = Modifier.fillMaxSize()
//                    )
                }
                is NetworkResponse.Success -> {
                    state.ratesListNetworkResponse.data?.let { ratesList ->
                        com.solonezowaty.currentrates.presentation.components.NbpRatesList(
                            ratesList = ratesList,
                            onDetailsClicked = { tableType, code ->
                                navController.navigate(
                                    com.solonezowaty.core.navigation.Destinations.CurrencyDetails.createRoute(
                                        tableType,
                                        code
                                    )
                                )
                            },
                            onPageScrolled = {
                                onAction(NbpRatesListAction.LoadNextPage)
                            }
                        )
                    }
//                    } ?: ErrorRetryComponent(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        onAction(NbpRatesListAction.Retry)
//                    }
                }
            }
        }
    }
}