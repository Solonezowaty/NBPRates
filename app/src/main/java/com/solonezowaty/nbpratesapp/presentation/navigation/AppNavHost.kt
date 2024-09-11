package com.solonezowaty.nbpratesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solonezowaty.nbpratesapp.presentation.rateslist.NbpRatesListScreen
import com.solonezowaty.nbpratesapp.presentation.rateslist.NbpRatesListViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.NbpRatesList.route
    ) {
        composable(Destinations.NbpRatesList.route) {
            val viewModel: NbpRatesListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            NbpRatesListScreen(
                navController = navController,
                state = state,
                onAction = viewModel::onAction
            )
        }
    }
}