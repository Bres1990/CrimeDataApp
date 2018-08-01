package com.apap.crimedataapp.map.repository

import android.util.Log
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

        call.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.d("LINK", call?.request().toString())
                Log.e("ERROR", "parseCountryData: failure" + " " + t.toString())
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    getCountry(response.toString())
                }
            }
        })

        return Observable.empty()
    }

    override fun getCountry(response: String): Observable<String>? {

        return Observable.just(response)
    }

}