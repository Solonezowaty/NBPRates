package com.solonezowaty.currentrates.domain.di

import com.solonezowaty.currentrates.domain.usecase.GetCurrentRatesUseCase
import com.solonezowaty.currentrates.domain.usecase.GetCurrentRatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentRatesDomainModule {

    @Binds
    abstract fun provideGetCurrentRatesUseCase(
        impl: GetCurrentRatesUseCaseImpl
    ): GetCurrentRatesUseCase
}