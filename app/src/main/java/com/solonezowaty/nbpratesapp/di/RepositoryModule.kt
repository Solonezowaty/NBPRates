package com.solonezowaty.nbpratesapp.di

import com.solonezowaty.nbpratesapp.data.remote.NbpRatesTableRepositoryImpl
import com.solonezowaty.nbpratesapp.domain.NbpRatesTableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideNbpRatesTableRepository(
        impl: NbpRatesTableRepositoryImpl
    ): NbpRatesTableRepository
}