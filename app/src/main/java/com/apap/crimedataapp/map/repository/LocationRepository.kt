package com.apap.crimedataapp.map.repository

import io.reactivex.Observable
import org.mapsforge.core.model.LatLong

interface LocationRepository {

    fun getCountry(location : LatLong) : Observable<String>
}