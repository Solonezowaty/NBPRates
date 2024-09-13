package com.solonezowaty.currentrates.data.di

import com.solonezowaty.currentrates.data.repository.RateTablesRepositoryImpl
import com.solonezowaty.currentrates.domain.repository.RateTablesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentRatesDataModule {

    @Binds
    @Singleton
    abstract fun provideRateTablesRepository(
        impl: RateTablesRepositoryImpl
    ): RateTablesRepository
}