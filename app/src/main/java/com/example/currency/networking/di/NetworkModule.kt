package com.example.currency.networking.di

import com.example.currency.networking.NetworkConfig
import com.example.currency.networking.NetworkManager
import com.example.currency.networking.NetworkManagerInterface
import com.example.currency.networking.ParametersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun retrofitService(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(NetworkConfig.baseUrl)
            .build()
    }
    @Provides
    @Singleton
    fun provideNetworkManager(): NetworkManagerInterface {
        return NetworkManager()
    }
    @Provides
    @Singleton
    fun providesLanguageInterceptor(): ParametersInterceptor {
        return ParametersInterceptor()
    }
    @Provides
    @Singleton
    fun okHttpClient(
        languageInterceptor: ParametersInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(languageInterceptor)
            .callTimeout(NetworkConfig.callTimeout, TimeUnit.SECONDS)
            .readTimeout(NetworkConfig.readTimeout, TimeUnit.SECONDS)
            .writeTimeout(NetworkConfig.writeTimeout, TimeUnit.SECONDS)
        return okHttpClient.build()
    }
}