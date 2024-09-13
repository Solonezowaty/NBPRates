package com.solonezowaty.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

interface NavDestination {
    val route: String
    val arguments: List<NamedNavArgument> get() = emptyList()

    @Composable
    fun Composable(navController: NavHostController, navBackStackEntry: NavBackStackEntry)
}

interface NavigationProvider {
    fun getDestinations(): List<NavDestination>
}