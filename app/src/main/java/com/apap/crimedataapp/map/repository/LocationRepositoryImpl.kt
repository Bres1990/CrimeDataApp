package com.apap.crimedataapp.map.repository

import android.util.Log
import com.apap.crimedataapp.data.LocationDetails
import com.apap.crimedataapp.map.fragment.CrimeMapFragment
import com.apap.crimedataapp.map.source.RemoteCountrySource
import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : LocationRepository {

    lateinit var countryName: String
    var fragment : CrimeMapFragment = CrimeMapFragment()

    override fun parseCountryData(location: LatLng) : Observable<String> {
        val remoteCountrySource = RemoteCountrySource.create()
        val call = remoteCountrySource.getCountry(location.latitude, location.longitude)

        call.enqueue(object: Callback<LocationDetails> {
            override fun onFailure(call: Call<LocationDetails>?, t: Throwable?) {
                Log.d("LINK", call?.request().toString())
                Log.e("ERROR", "parseCountryData: failure" + " " + t.toString())
            }

            override fun onResponse(call: Call<LocationDetails>?, response: Response<LocationDetails>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    val locationDetails: LocationDetails = response.body()!!
//                    countryName = locationDetails.toString()
//
//                    fragment.country.text = countryName
//                    Log.e("SUCCESS", countryName)
                }
            }
        })

        return Observable.empty()
    }
}