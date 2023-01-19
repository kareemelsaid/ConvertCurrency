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
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCaseInterface,
    private val convertCurrencyUseCase: ConvertCurrencyUseCaseInterface
) :
    ViewModel() {

    val getCurrencyLiveData = MutableLiveData<CurrencyResponse>()
    val currencyLoadingLiveData = MutableLiveData<Boolean>()
    val currencyErrorMessageLiveData = MutableLiveData<String>()
    val convertCurrencyLiveData = MutableLiveData<ConvertCurrencyResponse>()
    val convertCurrencyLoadingLiveData = MutableLiveData<Boolean>()
    val convertCurrencyErrorMessageLiveData = MutableLiveData<String>()

    fun getCurrency(context: Context) {
        currencyLoadingLiveData.value = true
        currencyUseCase.invoke(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CurrencyResponse> {
                override fun onNext(countriesStats: CurrencyResponse) {
                    getCurrencyLiveData.value = countriesStats
                }

                override fun onError(e: Throwable) {
                    currencyErrorMessageLiveData.value = e.message
                    currencyLoadingLiveData.value = false
                }

                override fun onComplete() {
                    currencyLoadingLiveData.value = false
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }

    fun convertCurrency(context: Context, from: String, amount: Double, to: String) {
        convertCurrencyLoadingLiveData.value = true
        convertCurrencyUseCase.invoke(context, from, amount, to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ConvertCurrencyResponse> {
                override fun onNext(convertCurrencyResponse: ConvertCurrencyResponse) {
                    convertCurrencyLiveData.value = convertCurrencyResponse
                }

                override fun onError(e: Throwable) {
                    convertCurrencyErrorMessageLiveData.value = e.message
                    convertCurrencyLoadingLiveData.value = false
                }

                override fun onComplete() {
                    convertCurrencyLoadingLiveData.value = false
                }

                override fun onSubscribe(d: Disposable) {
                }
            })
    }
}
