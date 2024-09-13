package com.solonezowaty.currentrates.presentation.di

import com.solonezowaty.core.navigation.NavigationProvider
import com.solonezowaty.currentrates.presentation.RatesListNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RatesListModule {
    @Binds
    abstract fun bindRatesListNavigationProvider(
        ratesListNavigationProvider: RatesListNavigationProvider
    ): NavigationProvider
}