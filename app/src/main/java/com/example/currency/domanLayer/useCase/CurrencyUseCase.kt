package com.example.currency.domanLayer.useCase

import android.content.Context
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.dataLayer.repo.CurrencyRepository
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.NetworkResource
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface CurrencyUseCaseInterface {

    operator fun invoke(
        context: Context
    ): Observable<CurrencyResponse>
}

class CurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepositoryInterface
) : CurrencyUseCaseInterface {

    override fun invoke(
        context: Context
    ): Observable<CurrencyResponse> {
        return repository.getCurrencyData()
    }
}