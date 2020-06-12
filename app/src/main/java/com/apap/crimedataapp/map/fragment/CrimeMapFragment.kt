package com.apap.crimedataapp.map.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.apap.crimedataapp.app.di.component.DaggerLocationComponent
import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.app.di.module.RepositoryModule
import com.apap.crimedataapp.base.BaseMapFragment
import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.presenter.LocationPresenter
import com.mapbox.mapboxsdk.annotations.PolygonOptions
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.crime_map_view.*
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject


class CrimeMapFragment : BaseMapFragment(), LocationContract.View {
    
    @Inject
    protected lateinit var locationPresenter: LocationPresenter

    lateinit var map: MapboxMap
    private var previouslyClickedRegion: MutableList<LatLng> = mutableListOf()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_map.onCreate(savedInstanceState)
        inject()

        crime_map.isClickable = true

        crime_map.getMapAsync { mapboxMap ->
            this.map = mapboxMap

            mapboxMap.setStyle(Style.Builder().fromUri("mapbox://styles/bres1990/cjktkm7660lke2sot77i1vbo1"))
            mapboxMap.getStyle {
                try {
                    it.addSource(GeoJsonSource("world", URI("https://cdn.jsdelivr.net/npm/world-atlas@2/countries-50m.json")))
                } catch (exception: URISyntaxException) {
                    Timber.d(exception)
                }
            }

            mapboxMap.addOnMapClickListener { point ->
                locationPresenter.getStateForLocation(point)
                false
            }

            mapboxMap.addOnMapLongClickListener { point ->
                val clickedRegion = mapboxMap.queryRenderedFeatures(mapboxMap.projection.toScreenLocation(point), "state_contours")
                if (clickedRegion.isNotEmpty())
                    locationPresenter.getBoundsForState(clickedRegion[0])
                false;
            }


        }
    }

    override fun handleError(error: String) {
        Timber.tag("").e(error)
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

    override fun highlightBounds(points: MutableList<LatLng>) {

        if (previouslyClickedRegion == points)
            return

        if (previouslyClickedRegion.isNotEmpty()) {
            colourRegion(previouslyClickedRegion, "#dedede")
        }

        colourRegion(points, "#e16b6b")
        previouslyClickedRegion = points
    }

    private fun colourRegion(region: MutableList<LatLng>, colour: String) {
        map.addPolygon(PolygonOptions()
                .addAll(region)
                .fillColor(Color.parseColor(colour)))

    }

}