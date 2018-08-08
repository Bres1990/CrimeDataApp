package com.apap.crimedataapp.map.repository

import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.Observable

interface LocationRepository {

    fun parseCountryData(location : LatLng) : Observable<String>
}