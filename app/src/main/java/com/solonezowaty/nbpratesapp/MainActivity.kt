package com.solonezowaty.nbpratesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.solonezowaty.nbpratesapp.presentation.navigation.AppNavHost
import com.solonezowaty.nbpratesapp.ui.theme.NbpRatesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NbpRatesAppTheme {
                AppNavHost(navController = rememberNavController())
            }
        }
    }
}