package com.example.currency.networking.common

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

interface CheckInternetConnectionInterface {

    fun isNetworkAvailable(context: Context): Boolean?
}

class CheckInternetConnection @Inject constructor() : CheckInternetConnectionInterface {

    override fun isNetworkAvailable(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}