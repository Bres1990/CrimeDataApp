package com.apap.crimedataapp.map.presenter

import android.util.Log
import com.apap.crimedataapp.map.contract.LocationContract
import org.mapsforge.core.model.LatLong
import javax.inject.Inject

class LocationPresenter : LocationContract.Presenter {

    @Inject
    constructor()

    lateinit var view : LocationContract.View

    override fun dispose() {
        //
    }

    override fun getCountryForLocation(coordinates: LatLong): String {
        val cords = coordinates.latitude.toString() + coordinates.longitude.toString()
        Log.d("coordinates", cords)
        return cords
    }

}