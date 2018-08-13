package com.apap.crimedataapp.map.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.apap.crimedataapp.app.di.component.DaggerLocationComponent
import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.app.di.module.RepositoryModule
import com.apap.crimedataapp.base.BaseMapFragment
import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.presenter.LocationPresenter
import com.mapbox.mapboxsdk.annotations.PolygonOptions
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import kotlinx.android.synthetic.main.crime_map_view.*
import javax.inject.Inject


class CrimeMapFragment : BaseMapFragment(), LocationContract.View {

    @Inject
    protected lateinit var locationPresenter: LocationPresenter

    lateinit var map: MapboxMap

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_map.onCreate(savedInstanceState)
        inject()

        crime_map.isClickable = true

        crime_map.getMapAsync { mapboxMap ->
            this.map = mapboxMap

            mapboxMap.addOnMapClickListener { point ->
                locationPresenter.getStateForLocation(point)
            }

            mapboxMap.addOnMapLongClickListener { point ->
                locationPresenter.getBoundsForState(point)


            }
        }
    }

    override fun handleError(error: String) {
        Log.e("", error)
    }

    override fun inject() {
        DaggerLocationComponent
                .builder()
                .locationModule(LocationModule(this))
                .repositoryModule(RepositoryModule())
                .build()
                .inject(this)
    }

    override fun returnState(state: String) {
        error_text.text = state
    }

    override fun highlightBounds(bounds: LatLngBounds) {
        val stateBounds: PolygonOptions = PolygonOptions()
                .add(bounds.northWest, bounds.northEast, bounds.southWest, bounds.southEast)
                .fillColor(Color.GREEN)
                .alpha(0.25f)

        Log.e(javaClass.simpleName, stateBounds.points.toString())
        map.addPolygon(stateBounds)


    }
}