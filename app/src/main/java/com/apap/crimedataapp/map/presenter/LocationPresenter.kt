package com.apap.crimedataapp.map.presenter

import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.interactor.GetCountryForLocationInteractor
import io.reactivex.disposables.CompositeDisposable
import org.mapsforge.core.model.LatLong
import javax.inject.Inject

class LocationPresenter : LocationContract.Presenter {

    var subscriptions: CompositeDisposable
    var view: LocationContract.View
    var getCountryForLocationInteractor: GetCountryForLocationInteractor

    @Inject constructor(view: LocationContract.View, getCountryForLocationInteractor: GetCountryForLocationInteractor) {
        this.view = view
        this.getCountryForLocationInteractor = getCountryForLocationInteractor

        subscriptions = CompositeDisposable()
    }

    override fun getCountryForLocation(coordinates: LatLong) {
        subscriptions.add(getCountryForLocationInteractor.execute(coordinates).subscribe({data -> view.returnCountry(data)}))
    }

    override fun dispose() {
        subscriptions.dispose()
        subscriptions.clear()
    }
}