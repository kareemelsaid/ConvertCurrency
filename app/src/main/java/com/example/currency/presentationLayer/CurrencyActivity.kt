package com.example.currency.presentationLayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currency.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}