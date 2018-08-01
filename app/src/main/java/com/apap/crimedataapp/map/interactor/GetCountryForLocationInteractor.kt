package com.apap.crimedataapp.map.interactor

import com.apap.crimedataapp.base.BaseInteractor
import com.apap.crimedataapp.map.repository.LocationRepository
import io.reactivex.Observable
import org.mapsforge.core.model.LatLong
import javax.inject.Inject

class GetCountryForLocationInteractor @Inject constructor(var locationRepository: LocationRepository) : BaseInteractor<String, LatLong>() {

    override fun buildObservable(p: LatLong): Observable<String> {
        return locationRepository.parseCountryData(p)
    }
}