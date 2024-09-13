package com.solonezowaty.currencydetails.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.solonezowaty.core.utils.NetworkResponse
import com.solonezowaty.currencydetails.presentation.components.CurrencyHistoryItem

@Composable
fun CurrencyDetailsScreen(
    navController: NavController,
    state: CurrencyDetailsViewState
) {
    Scaffold(
        topBar = {
//            com.solonezowaty.core.presentation.ui.NbpRatesTopBar(
//                navController = navController,
//                screenTitle = state.currencyDetails.data?.currency ?: "",
//                screenSubTitle = state.currencyDetails.data?.code ?: "",
//                isBackAvailable = true
//            )
        }
    ) { innerPadding ->
        when(state.currencyDetails) {
            is NetworkResponse.Error -> {
//                ErrorRetryComponent(
//                    modifier = Modifier
//                        .padding(innerPadding)
//                        .fillMaxSize()
//                ) {
//
//                }
            }
            is NetworkResponse.Loading -> {
//                LoadingProgressBar(
//                    modifier = Modifier
//                        .padding(innerPadding)
//                        .fillMaxSize()
//                )
            }
            is NetworkResponse.Success -> {
                state.currencyDetails.data?.ratesList?.let { rates ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        itemsIndexed(
                            rates,
                            key = { _, item -> item.date}
                        ) { index, rate ->
                            CurrencyHistoryItem(rate)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChallengeDetailScreenPreview() {
    CurrencyDetailsScreen(
        navController = rememberNavController(),
        state = CurrencyDetailsViewState()
    )
}