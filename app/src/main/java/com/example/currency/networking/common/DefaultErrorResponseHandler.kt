package com.example.currency.networking.common

import javax.inject.Inject

interface ErrorResponseHandlerInterface {

    fun handleDefaultError(statusCode: Int): String
}

class DefaultErrorResponseHandler @Inject constructor() : ErrorResponseHandlerInterface {

    override fun handleDefaultError(statusCode: Int): String {
        var message = ""
        when (statusCode) {
            429 -> {
                message = "Invalid API key"
            }
            500 -> {
                message = "Internal server error"
            }
            503 -> {
                message = "No Internet Connection"
            }
        }
        return message
    }
}