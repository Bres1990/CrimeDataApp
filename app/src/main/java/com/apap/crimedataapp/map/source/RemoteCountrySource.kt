package com.apap.crimedataapp.map.source

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteCountrySource {

    // https://nominatim.openstreetmap.org/  reverse?format=json&lat=XX.XX&lon=YY.YY
    @GET(value = "reverse?format=json")
    fun getCountry(@Query("lat") lat: Double, @Query("lon") lon: Double) : Call<String>

    companion object Factory {
        fun create(): RemoteCountrySource {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://nominatim.openstreetmap.org/")
                    .build()

            return retrofit.create(RemoteCountrySource::class.java)
        }
    }
}