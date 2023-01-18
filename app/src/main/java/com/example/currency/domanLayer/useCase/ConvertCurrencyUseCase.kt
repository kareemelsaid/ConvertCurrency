package com.example.currency.domanLayer.useCase

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.NetworkResource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface ConvertCurrencyUseCaseInterface {

     operator fun invoke(
        context: Context,
        from: String,
        amount: Double,
        to: String
    ): Observable<NetworkResource<ConvertCurrencyResponse>>
}

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepositoryInterface,
    private val checkInternetConnection: CheckInternetConnectionInterface,
) : ConvertCurrencyUseCaseInterface {

    override  fun invoke(
        context: Context,
        from: String,
        amount: Double,
        to: String
    ): Observable<NetworkResource<ConvertCurrencyResponse>> {
        return repository.convertCurrency(from, amount, to)
    }
}