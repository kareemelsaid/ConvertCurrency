package com.example.currency.dataLayer.repo

import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.dataLayer.repo.dataSource.CurrencyRemoteDataSourceInterface
import com.example.currency.networking.NetworkManagerInterface
import com.example.currency.networking.common.NetworkResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrencyRepositoryInterface {

    suspend fun getCurrencyData(): Flow<NetworkResource<CurrencyResponse>>

    suspend fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Flow<NetworkResource<ConvertCurrencyResponse>>
}

class CurrencyRepository @Inject constructor(
    private val currencyRemoteDataSourceInterface: CurrencyRemoteDataSourceInterface,
    private val networkManager: NetworkManagerInterface
) : CurrencyRepositoryInterface {

    override suspend fun getCurrencyData(): Flow<NetworkResource<CurrencyResponse>> {
        return networkManager.performRequest(
            request = {
                currencyRemoteDataSourceInterface.getCurrency()
            })
    }

    override suspend fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Flow<NetworkResource<ConvertCurrencyResponse>> {
        return networkManager.performRequest(
            request = {
                currencyRemoteDataSourceInterface.convertCurrency(from, amount, to)
            })
    }
}