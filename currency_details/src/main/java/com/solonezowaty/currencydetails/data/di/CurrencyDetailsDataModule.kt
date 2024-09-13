package com.solonezowaty.currencydetails.data.di

import com.solonezowaty.currencydetails.data.repository.CurrencyDetailsRepositoryImpl
import com.solonezowaty.currencydetails.domain.repositories.CurrencyDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyDetailsDataModule {

    @Binds
    @Singleton
    abstract fun provideCurrencyDetailsRepository(
        impl: CurrencyDetailsRepositoryImpl
    ): CurrencyDetailsRepository
}