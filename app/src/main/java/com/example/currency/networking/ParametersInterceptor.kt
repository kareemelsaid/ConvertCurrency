package com.example.currency.networking

import okhttp3.Interceptor
import okhttp3.Response

class ParametersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.method(request.method, request.body)
            .addHeader("Accept-Language", getCurrentLanguage()).addHeader("apikey","N4m0VRT0AuUUt62acFxtMU1RJj6wM205")
        return chain.proceed(builder.build())
    }

    private fun getCurrentLanguage(): String {
        return "en"
    }
}


