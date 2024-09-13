package com.solonezowaty.core.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideNavigationDestinations(
        providers: Set<@JvmSuppressWildcards NavigationProvider>
    ): List<NavDestination> {
        return providers.flatMap { it.getDestinations() }
    }
}