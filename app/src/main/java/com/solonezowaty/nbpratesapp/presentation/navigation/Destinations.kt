package com.solonezowaty.nbpratesapp.presentation.navigation

sealed class Destinations(val route: String) {
    data object NbpRatesList : Destinations("nbp_rates_list")
}