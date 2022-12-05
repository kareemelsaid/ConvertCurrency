package com.example.currency.networking

import com.example.currency.networking.common.NetworkResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface NetworkManagerInterface {

    fun <T : Any> performRequest(
        request: suspend () -> Response<T>,
    ): Flow<NetworkResource<T>>
}

class NetworkManager : NetworkManagerInterface {

    override fun <T : Any> performRequest(
        request: suspend () -> Response<T>,
    ): Flow<NetworkResource<T>> {
        return flow {
            emit(NetworkResource.Loading<T>())
            val response = request.invoke()
            when (val result = verifyResponse(response)) {
                is NetworkResource.Loading -> emit(NetworkResource.Loading<T>())
                is NetworkResource.Success -> emit(NetworkResource.Success<T>(result.data))
                is NetworkResource.Error -> result.message.let {
                    NetworkResource.Error<T>(
                        it,
                        statusCode = response.code()
                    )
                }
                    .let { emit(it) }
                else -> {}
            }
        }
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