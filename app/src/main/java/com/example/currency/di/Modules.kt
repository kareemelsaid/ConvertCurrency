package com.example.currency.di

import com.example.currency.dataLayer.repo.CurrencyRepository
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.dataLayer.repo.dataSource.CurrencyRemoteDataSource
import com.example.currency.dataLayer.repo.dataSource.CurrencyRemoteDataSourceInterface
import com.example.currency.domanLayer.useCase.ConvertCurrencyUseCase
import com.example.currency.domanLayer.useCase.ConvertCurrencyUseCaseInterface
import com.example.currency.domanLayer.useCase.CurrencyUseCase
import com.example.currency.domanLayer.useCase.CurrencyUseCaseInterface
import com.example.currency.networking.common.CheckInternetConnection
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.DefaultErrorResponseHandler
import com.example.currency.networking.common.ErrorResponseHandlerInterface
import com.example.currency.networking.di.NetworkModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class CurrencyModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyRemoteDataSourceInterface(currencyRemoteDataSource: CurrencyRemoteDataSource): CurrencyRemoteDataSourceInterface
    @Binds
    @Singleton
    abstract fun bindCurrencyRepositoryInterface(currencyRepository: CurrencyRepository): CurrencyRepositoryInterface
    @Binds
    @Singleton
    abstract fun bindCurrencyUseCaseInterface(currencyUseCase: CurrencyUseCase): CurrencyUseCaseInterface
    @Binds
    @Singleton
    abstract fun bindConvertCurrencyUseCaseInterface(convertCurrencyUseCase: ConvertCurrencyUseCase): ConvertCurrencyUseCaseInterface
    @Binds
    @Singleton
    abstract fun bindCheckInternetConnectionInterface(checkInternetConnection: CheckInternetConnection): CheckInternetConnectionInterface
    @Binds
    @Singleton
    abstract fun bindErrorResponseHandlerInterface(defaultErrorResponseHandler: DefaultErrorResponseHandler): ErrorResponseHandlerInterface
}

