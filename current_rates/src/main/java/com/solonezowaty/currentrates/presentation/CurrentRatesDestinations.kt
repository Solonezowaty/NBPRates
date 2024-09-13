package com.solonezowaty.currentrates.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.solonezowaty.core.navigation.NavDestination
import com.solonezowaty.core.navigation.NavigationProvider

class RatesListNavigationProvider : NavigationProvider {
    override fun getDestinations(): List<NavDestination> = listOf(
        object : NavDestination {
            override val route: String = "rates_list"

            @Composable
            override fun Composable(
                navController: NavHostController,
                navBackStackEntry: NavBackStackEntry
            ) {
                val viewModel: NbpRatesListViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()

                NbpRatesListScreen(
                    navController = navController,
                    state = state,
                    onAction = viewModel::onAction
                )
            }
        }
    )
}