package com.apap.crimedataapp.map.interactor

import com.apap.crimedataapp.base.BaseInteractor
import com.apap.crimedataapp.map.repository.LocationRepository
import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.Observable
import javax.inject.Inject

class GetCountryForLocationInteractor @Inject constructor(var locationRepository: LocationRepository) : BaseInteractor<String, LatLng>() {

    override fun buildObservable(p: LatLng): Observable<String> {

        return locationRepository.parseCountryData(p)
    }
}