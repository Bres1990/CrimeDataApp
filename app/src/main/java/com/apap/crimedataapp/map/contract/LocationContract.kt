package com.apap.crimedataapp.map.contract

import com.apap.crimedataapp.base.BasePresenter
import com.apap.crimedataapp.base.BaseView
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds

class LocationContract {

    interface View : BaseView {
        fun returnState(state: String)
        fun highlightBounds(bounds: LatLngBounds)
    }

    interface Presenter : BasePresenter {
        fun getStateForLocation(coordinates: LatLng)
        fun getBoundsForState(coordinates: LatLng)
    }
}
