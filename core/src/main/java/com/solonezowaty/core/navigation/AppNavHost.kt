package com.solonezowaty.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

//@Composable
//fun AppNavHost(
//    navController: NavHostController
//) {
//    NavHost(
//        navController = navController,
//        startDestination = Destinations.RatesList.route
//    ) {
//        composable(Destinations.RatesList.route) {
//            val viewModel: NbpRatesListViewModel = hiltViewModel()
//            val state by viewModel.state.collectAsState()
//            NbpRatesListScreen(
//                navController = navController,
//                state = state,
//                onAction = viewModel::onAction
//            )
//        }
//        composable(
//            route = Destinations.CurrencyDetails.route,
//            arguments = listOf(
//                navArgument("tableType") { type = NavType.StringType },
//                navArgument("code") { type = NavType.StringType }
//            )
//        ) {
//            navBackStackEntry ->
//            val tableType = remember {
//                navBackStackEntry.arguments?.getString("tableType") ?: ""
//            }
//            val code = remember {
//                navBackStackEntry.arguments?.getString("code") ?: ""
//            }
//            val viewModel: CurrencyDetailsViewModel = hiltViewModel()
//            val state by viewModel.state.collectAsState()
//
//            CurrencyDetailsScreen(
//                navController = navController,
//                state = state
//            )
//        }
//    }
//}

@Composable
fun AppNavHost(
    navController: NavHostController,
    destinations: List<NavDestination>
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.RatesList.route
    ) {
        destinations.forEach { destination ->
            composable(
                route = destination.route,
                arguments = destination.arguments
            ) { navBackStackEntry ->
                destination.Composable(navController, navBackStackEntry)
            }
        }
    }
}