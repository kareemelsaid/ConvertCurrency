package com.example.currency.dataLayer.repo

import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.dataLayer.repo.dataSource.CurrencyRemoteDataSourceInterface
import com.example.currency.networking.NetworkManagerInterface
import com.example.currency.networking.common.NetworkResource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrencyRepositoryInterface {

     fun getCurrencyData(): Observable<NetworkResource<CurrencyResponse>>

     fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Observable<NetworkResource<ConvertCurrencyResponse>>
}

class CurrencyRepository @Inject constructor(
    private val currencyRemoteDataSourceInterface: CurrencyRemoteDataSourceInterface,
    private val networkManager: NetworkManagerInterface
) : CurrencyRepositoryInterface {

    override fun getCurrencyData(): Observable<NetworkResource<CurrencyResponse>> {
        return networkManager.performRequest(
            request = {
                currencyRemoteDataSourceInterface.getCurrency()
            })
    }

    override  fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Observable<NetworkResource<ConvertCurrencyResponse>> {
        return networkManager.performRequest(
            request = {
                currencyRemoteDataSourceInterface.convertCurrency(from, amount, to)
            })
    }
}