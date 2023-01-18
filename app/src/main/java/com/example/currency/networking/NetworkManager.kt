package com.example.currency.networking

import com.example.currency.networking.common.NetworkResource
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

interface NetworkManagerInterface {

    fun <T : Any> performRequest(
        request: () -> Response<T>,
    ): Observable<NetworkResource<T>>
}

class NetworkManager : NetworkManagerInterface {

    override fun <T : Any> performRequest(
        request: () -> Response<T>,
    ): Observable<NetworkResource<T>> {
        return Observable.create(ObservableOnSubscribe { emitter ->
            emitter.onNext(NetworkResource.Loading<T>())
            val response = request.invoke()
            when (val result = verifyResponse(response)) {
                is NetworkResource.Loading -> emitter.onNext(NetworkResource.Loading<T>())
                is NetworkResource.Success -> emitter.onNext(NetworkResource.Success<T>(result.data))
                is NetworkResource.Error -> result.message.let {
                    NetworkResource.Error<T>(
                        it,
                        statusCode = response.code()
                    )
                }
                    .let { emitter.onNext(it) }
                else -> {}
            }
        }).subscribeOn(Schedulers.io())
    }

    private fun <T : Any> verifyResponse(response: Response<T>): NetworkResource<T> {
        return try {
            val isResponseSuccessful = response.isSuccessful
            val statusCode = response.code()
            val responseBody = response.body()
            val errorBodyMessage = response.errorBody()?.string()
            if (isResponseSuccessful) {
                NetworkResource.Success(responseBody)
            } else {
                val message = Gson().fromJson(errorBodyMessage, Message::class.java)
                message.statusCode = statusCode
                NetworkResource.Error(message.toString(), statusCode = statusCode)
            }
        } catch (ex: Exception) {
            NetworkResource.Error(
                Message(ex.localizedMessage as String).toString(),
                statusCode = response.code()
            )
        }
    }
}