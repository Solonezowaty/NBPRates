package com.solonezowaty.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.solonezowaty.core.navigation.AppNavHost
import com.solonezowaty.core.navigation.NavDestination
import com.solonezowaty.core.ui.theme.NbpRatesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject
    lateinit var destinations: List<NavDestination>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NbpRatesAppTheme {
                AppNavHost(
                    navController = rememberNavController(),
                    destinations = destinations
                )
            }
        }
    }
}