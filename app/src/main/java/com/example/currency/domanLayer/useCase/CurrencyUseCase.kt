package com.example.currency.domanLayer.useCase

import android.content.Context
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.dataLayer.repo.CurrencyRepository
import com.example.currency.dataLayer.repo.CurrencyRepositoryInterface
import com.example.currency.networking.common.CheckInternetConnectionInterface
import com.example.currency.networking.common.NetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface CurrencyUseCaseInterface {

    suspend operator fun invoke(
        context: Context
    ): Flow<NetworkResource<CurrencyResponse>>
}

class CurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepositoryInterface,
    private val checkInternetConnection: CheckInternetConnectionInterface,
) : CurrencyUseCaseInterface {

    override suspend fun invoke(
        context: Context
    ): Flow<NetworkResource<CurrencyResponse>> {
        return if (checkInternetConnection.isNetworkAvailable(context) == true) {
            repository.getCurrencyData()
        } else {
            noInternetConnection()
        }
    }

    private fun noInternetConnection(): Flow<NetworkResource.Error<Nothing>> {
        return flowOf(NetworkResource.Error(message = "No Internet Connection", statusCode = 503))
    }
}