package com.apap.crimedataapp.map.repository

import com.apap.crimedataapp.map.source.RemoteCountrySource
import io.reactivex.Observable
import org.mapsforge.core.model.LatLong

class LocationRepositoryImpl : LocationRepository {

    private lateinit var remoteCountrySource: RemoteCountrySource

    override fun getCountry(location: LatLong): Observable<String> {
        return Observable.just(remoteCountrySource.getCountry(location.latitude, location.longitude))
    }

}