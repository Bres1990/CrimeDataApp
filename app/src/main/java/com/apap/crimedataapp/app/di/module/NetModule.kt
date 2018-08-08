package com.apap.crimedataapp.app.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module class NetModule {

    private val okHttpClient: OkHttpClient = OkHttpClient()
    private val BASE_URL = ""

    @Provides @Singleton fun providesOkHttpClient() : OkHttpClient {
        return okHttpClient
    }

    @Provides @Singleton fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }
}