package com.solonezowaty.currencydetails.domain.di

import com.solonezowaty.currencydetails.domain.usecases.GetRateDetailsUseCase
import com.solonezowaty.currencydetails.domain.usecases.GetRateDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyDetailsDomainModule {

    @Binds
    abstract fun provideGetRateDetailsUseCase(
        impl: GetRateDetailsUseCaseImpl
    ): GetRateDetailsUseCase
}