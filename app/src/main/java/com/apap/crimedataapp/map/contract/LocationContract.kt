package com.apap.crimedataapp.map.contract

import com.apap.crimedataapp.base.BasePresenter
import com.apap.crimedataapp.base.BaseView
import com.mapbox.mapboxsdk.geometry.LatLng

class LocationContract {

    interface View : BaseView {
        fun returnState(state: String)
        fun displayStateChoiceDialog(state: String)
    }

    interface Presenter : BasePresenter {
        fun getStateForLocation(coordinates: LatLng)
        fun chooseState(name: String)
    }
}
