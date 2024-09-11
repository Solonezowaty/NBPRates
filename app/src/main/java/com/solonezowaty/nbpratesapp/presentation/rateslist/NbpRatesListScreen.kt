package com.solonezowaty.nbpratesapp.presentation.rateslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.solonezowaty.nbpratesapp.presentation.common.ui.ErrorRetryComponent
import com.solonezowaty.nbpratesapp.presentation.common.ui.LoadingProgressBar
import com.solonezowaty.nbpratesapp.presentation.rateslist.components.NbpRatesList
import com.solonezowaty.nbpratesapp.utils.NetworkResponse

@Composable
fun NbpRatesListScreen(
    navController: NavController,
    state: NbpRatesListViewState,
    onAction: (action: NbpRatesListAction) -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when(state.ratesListNetworkResponse) {
                is NetworkResponse.Error -> {
                    ErrorRetryComponent(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        onAction(NbpRatesListAction.Retry)
                    }
                }
                is NetworkResponse.Loading -> {
                    LoadingProgressBar(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is NetworkResponse.Success -> {
                    state.ratesListNetworkResponse.data?.let { ratesList ->
                       NbpRatesList(
                           ratesList = ratesList,
                           onDetailsClicked = {  },
                           onPageScrolled = {
                               onAction(NbpRatesListAction.LoadNextPage)
                           }
                       )
                    } ?: ErrorRetryComponent(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        onAction(NbpRatesListAction.Retry)
                    }
                }
            }
        }
    }
}