package com.example.currency.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currency.dataLayer.model.ConvertCurrencyResponse
import com.example.currency.dataLayer.model.CurrencyResponse
import com.example.currency.domanLayer.useCase.ConvertCurrencyUseCaseInterface
import com.example.currency.domanLayer.useCase.CurrencyUseCaseInterface
import com.example.currency.networking.common.ErrorResponseHandlerInterface
import com.example.currency.networking.common.NetworkResource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCaseInterface,
    private val convertCurrencyUseCase: ConvertCurrencyUseCaseInterface,
    private val errorResponseHandlerInterface: ErrorResponseHandlerInterface,
) :
    ViewModel() {

    private val getCurrencyState = MutableLiveData<NetworkResource<CurrencyResponse?>>()
    private val _convertCurrencyState =
        MutableStateFlow<NetworkResource<ConvertCurrencyResponse>>(NetworkResource.Noun())
    val convertCurrencyState = _convertCurrencyState.asStateFlow()

    fun getCurrency(context: Context) {
        val observable = currencyUseCase.invoke(context).subscribeOn(AndroidSchedulers.mainThread())
        observable.subscribe(getCurrencyState.)
    }
    }

    fun convertCurrency(context: Context,from: String, amount: Double, to: String) {
//        viewModelScope.launch {
//            convertCurrencyUseCase(context,from, amount, to).collect {
//                when (it) {
//                    is NetworkResource.Success -> {
//                        _convertCurrencyState.value = NetworkResource.Success(it.data)
//                    }
//                    is NetworkResource.Error -> {
//                        val message = errorResponseHandlerInterface.handleDefaultError(it.statusCode)
//                        _convertCurrencyState.value = NetworkResource.Error(message, statusCode = it.statusCode)
//                    }
//                    is NetworkResource.Loading -> {
//                        _convertCurrencyState.value = NetworkResource.Loading()
//                    }
//                    else -> {}
//                }
//            }
//        }
    }
