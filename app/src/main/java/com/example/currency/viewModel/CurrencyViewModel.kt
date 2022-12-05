package com.example.currency.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.domanLayer.useCase.ConvertCurrencyUseCaseInterface
import com.example.currency.domanLayer.useCase.CurrencyUseCaseInterface
import com.example.currency.networking.common.ErrorResponseHandlerInterface
import com.example.currency.networking.common.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCaseInterface,
    private val convertCurrencyUseCase: ConvertCurrencyUseCaseInterface,
    private val errorResponseHandlerInterface: ErrorResponseHandlerInterface,
) :
    ViewModel() {

    private val _getCurrencyState =
        MutableStateFlow<NetworkResource<CurrencyResponse?>>(NetworkResource.Loading())
    val getCurrencyState = _getCurrencyState.asStateFlow()
    private val _convertCurrencyState =
        MutableStateFlow<NetworkResource<ConvertCurrencyResponse>>(NetworkResource.Noun())
    val convertCurrencyState = _convertCurrencyState.asStateFlow()

    fun getCurrency(context: Context) {
        viewModelScope.launch {
            currencyUseCase(context).collect {
                when (it) {
                    is NetworkResource.Success -> {
                        _getCurrencyState.value = NetworkResource.Success(it.data)
                    }
                    is NetworkResource.Error -> {
                        val message = errorResponseHandlerInterface.handleDefaultError(it.statusCode)
                        _getCurrencyState.value = NetworkResource.Error(message = message, statusCode = it.statusCode)
                    }
                    is NetworkResource.Loading -> {
                        _getCurrencyState.value = NetworkResource.Loading()
                    }
                    else -> {}
                }
            }
        }
    }

    fun convertCurrency(context: Context,from: String, amount: Double, to: String) {
        viewModelScope.launch {
            convertCurrencyUseCase(context,from, amount, to).collect {
                when (it) {
                    is NetworkResource.Success -> {
                        _convertCurrencyState.value = NetworkResource.Success(it.data)
                    }
                    is NetworkResource.Error -> {
                        val message = errorResponseHandlerInterface.handleDefaultError(it.statusCode)
                        _convertCurrencyState.value = NetworkResource.Error(message, statusCode = it.statusCode)
                    }
                    is NetworkResource.Loading -> {
                        _convertCurrencyState.value = NetworkResource.Loading()
                    }
                    else -> {}
                }
            }
        }
    }
}