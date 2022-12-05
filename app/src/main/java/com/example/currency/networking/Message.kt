package com.example.currency.networking

import com.google.gson.annotations.SerializedName

data class Message(@SerializedName("error") var errorMessage: String = "", var statusCode: Int = 0)