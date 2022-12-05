package com.example.currency.dataLayer.repo.dataSource

import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Inject

interface CurrencyApi {

    @GET("https://api.apilayer.com/currency_data/list")
    suspend fun getCurrency(): Response<CurrencyResponse>

    @GET("https://api.apilayer.com/currency_data/convert&from={from}&amount={amount}")
    suspend fun convertCurrency(
        @Path("from") from: String,
        @Path("amount") amount: Double,
        @Query("to") to: String
    ): Response<ConvertCurrencyResponse>
}

interface CurrencyRemoteDataSourceInterface {

    suspend fun getCurrency(
    ): Response<CurrencyResponse>

    suspend fun convertCurrency(
        from: String, amount: Double,to: String
    ): Response<ConvertCurrencyResponse>
}

class CurrencyRemoteDataSource @Inject constructor(retrofit: Retrofit) :
    CurrencyRemoteDataSourceInterface {

    private val api = retrofit.create(CurrencyApi::class.java)
    override suspend fun getCurrency(
    ): Response<CurrencyResponse> {
        return api.getCurrency()
    }

    override suspend fun convertCurrency(
        from: String,
        amount: Double,
        to: String
    ): Response<ConvertCurrencyResponse> {
        return api.convertCurrency(from, amount, to)
    }
}