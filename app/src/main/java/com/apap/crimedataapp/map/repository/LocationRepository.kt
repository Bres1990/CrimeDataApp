package com.apap.crimedataapp.map.repository

import io.reactivex.Observable
import org.mapsforge.core.model.LatLong

interface LocationRepository {

    fun parseCountryData(location : LatLong) : Observable<String>
    fun getCountry(response : String) : Observable<String>?
}