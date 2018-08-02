package com.apap.crimedataapp.map.repository

import android.util.Log
import com.apap.crimedataapp.data.LocationDetails
import com.apap.crimedataapp.map.source.RemoteCountrySource
import io.reactivex.Observable
import org.mapsforge.core.model.LatLong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : LocationRepository {

    override fun parseCountryData(location: LatLong) : Observable<String> {
        val remoteCountrySource = RemoteCountrySource.create()
        val call = remoteCountrySource.getCountry(location.latitude, location.longitude)
        var responseData : Observable<String> = Observable.empty()

        call.enqueue(object: Callback<LocationDetails> {
            override fun onFailure(call: Call<LocationDetails>?, t: Throwable?) {
                Log.d("LINK", call?.request().toString())
                Log.e("ERROR", "parseCountryData: failure" + " " + t.toString())
            }

            override fun onResponse(call: Call<LocationDetails>?, response: Response<LocationDetails>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    val locationDetails: LocationDetails = response.body()!!
                    val countryName = locationDetails.toString()
                    Log.e("SUCCESS", countryName)
                    responseData = getCountry(countryName)
                }
            }
        })

        return responseData
    }

    override fun getCountry(response: String): Observable<String> {
        Log.e("LocationRepositoryImpl", response)

        return Observable.just(response)
    }

}