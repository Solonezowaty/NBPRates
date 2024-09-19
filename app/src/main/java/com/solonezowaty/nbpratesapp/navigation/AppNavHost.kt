package com.solonezowaty.nbpratesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solonezowaty.core.Destinations
import com.solonezowaty.currencydetails.presentation.CurrencyDetailsScreen
import com.solonezowaty.currencydetails.presentation.CurrencyDetailsViewModel
import com.solonezowaty.currentrates.presentation.CurrentRatesScreen
import com.solonezowaty.currentrates.presentation.CurrentRatesViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.RatesList.route
    ) {
        composable(Destinations.RatesList.route) {
            val viewModel: CurrentRatesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            CurrentRatesScreen(
                navController = navController,
                state = state,
                onAction = viewModel::onAction
            )
        }
        composable(
            route = Destinations.CurrencyDetails.route,
            arguments = listOf(
                navArgument("tableType") { type = NavType.StringType },
                navArgument("code") { type = NavType.StringType }
            )
        ) {
            navBackStackEntry ->
            val tableType = remember {
                navBackStackEntry.arguments?.getString("tableType") ?: ""
            }
            val code = remember {
                navBackStackEntry.arguments?.getString("code") ?: ""
            }
            val viewModel: CurrencyDetailsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()

            CurrencyDetailsScreen(
                navController = navController,
                state = state,
                onAction = viewModel::onAction
            )
        }
    }
}