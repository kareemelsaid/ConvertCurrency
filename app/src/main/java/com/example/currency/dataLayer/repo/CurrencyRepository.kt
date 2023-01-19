package com.example.currency.dataLayer.repo

import android.annotation.SuppressLint
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.dataLayer.repo.dataSource.CurrencyRemoteDataSourceInterface
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


interface CurrencyRepositoryInterface {
    fun getCurrencyData(): Observable<CurrencyResponse>

    fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Observable<ConvertCurrencyResponse>
}

class CurrencyRepository @Inject constructor(
    private val currencyRemoteDataSourceInterface: CurrencyRemoteDataSourceInterface,
) : CurrencyRepositoryInterface {

    @SuppressLint("CheckResult")
    override fun getCurrencyData(): Observable<CurrencyResponse> {
        return currencyRemoteDataSourceInterface.getCurrency()
    }

    override fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Observable<ConvertCurrencyResponse> {
        return currencyRemoteDataSourceInterface.convertCurrency(from, amount, to)
    }
}