package com.apap.crimedataapp.map.source

import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteCountrySource {

    // https://nominatim.openstreetmap.org/  reverse?format=json&lat=51.0&lon=17.0
    @GET(value = "reverse?format=json&lat={lat}&lon={lon}")
    fun getCountry(@Path("lat") lat: Double, @Path("lon") lon: Double) : String
}