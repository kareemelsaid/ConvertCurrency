package com.example.currency.domanLayer.useCase

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.NetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface ConvertCurrencyUseCaseInterface {

    suspend operator fun invoke(
        context: Context,
        from: String,
        amount: Double,
        to: String
    ): Flow<NetworkResource<ConvertCurrencyResponse>>
}

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepositoryInterface,
    private val checkInternetConnection: CheckInternetConnectionInterface,
) : ConvertCurrencyUseCaseInterface {

    override suspend fun invoke(
        context: Context,
        from: String,
        amount: Double,
        to: String
    ): Flow<NetworkResource<ConvertCurrencyResponse>> {
        return if (checkInternetConnection.isNetworkAvailable(context) == true) {
            repository.convertCurrency(from, amount, to)
        } else {
            noInternetConnection()
        }
    }

    private fun noInternetConnection(): Flow<NetworkResource.Error<Nothing>> {
        return flowOf(NetworkResource.Error(message = "No Internet Connection", statusCode = 503))
    }
}