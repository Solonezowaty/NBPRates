package com.solonezowaty.currentrates.domain.di

import com.solonezowaty.currentrates.domain.usecase.GetRatesUseCase
import com.solonezowaty.currentrates.domain.usecase.GetRatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentRatesDomainModule {

    @Binds
    abstract fun provideGetRatesUseCase(
        impl: GetRatesUseCaseImpl
    ): GetRatesUseCase
}