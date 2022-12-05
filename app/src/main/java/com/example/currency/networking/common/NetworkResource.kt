package com.example.currency.networking.common

sealed class NetworkResource<out T>(open val data: T? = null, open val message: String? = null) {
    data class Success<T>(override val data: T?) : NetworkResource<T>(data)
    data class Error<T>(override val message: String?, override val data: T? = null, val statusCode: Int) : NetworkResource<T>(data, message)
    class Loading<T>() : NetworkResource<T>()
    class Noun<T>() : NetworkResource<T>()
}