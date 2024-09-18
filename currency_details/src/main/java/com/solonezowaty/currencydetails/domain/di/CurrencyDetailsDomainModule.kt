package com.solonezowaty.currencydetails.domain.di

import com.solonezowaty.currencydetails.domain.usecases.GetCurrencyDetailsUseCase
import com.solonezowaty.currencydetails.domain.usecases.GetCurrencyDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyDetailsDomainModule {

    @Binds
    abstract fun provideGetCurrencyDetailsUseCase(
        impl: GetCurrencyDetailsUseCaseImpl
    ): GetCurrencyDetailsUseCase
}