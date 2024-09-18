package com.solonezowaty.currentrates.data.di

import com.solonezowaty.currentrates.data.remote.CurrentRatesApi
import com.solonezowaty.currentrates.data.repository.CurrentRatesRepositoryImpl
import com.solonezowaty.currentrates.domain.repository.CurrentRatesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentRatesDataModule {

    @Binds
    @Singleton
    abstract fun provideCurrentRatesRepository(
        impl: CurrentRatesRepositoryImpl
    ): CurrentRatesRepository

    companion object {
        @Provides
        @Singleton
        fun provideCurrentRatesApi(retrofit: Retrofit) : CurrentRatesApi =
            retrofit.create(CurrentRatesApi::class.java)
    }
}