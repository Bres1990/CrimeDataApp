package com.apap.crimedataapp.map.fragment

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apap.crimedataapp.R
import com.apap.crimedataapp.app.di.component.DaggerLocationComponent
import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.app.di.module.RepositoryModule
import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.presenter.LocationPresenter
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.crime_map_view.*
import javax.inject.Inject


class CrimeMapFragment : Fragment(), LocationContract.View {

    @Inject
    protected lateinit var locationPresenter: LocationPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Mapbox.getInstance(this.activity, getString(R.string.access_token))
        return inflater.inflate(R.layout.crime_map_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crime_map.onCreate(savedInstanceState)
        inject()

        crime_map.isClickable = true
        crime_map.getMapAsync { mapboxMap ->
            mapboxMap!!.setOnMapClickListener { point ->
                locationPresenter.getCountryForLocation(point)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        crime_map.onStart()
    }

    override fun onResume() {
        super.onResume()
        crime_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        crime_map.onPause()
    }

    override fun onStop() {
        super.onStop()
        crime_map.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        crime_map.onLowMemory()
    }

    override fun onDestroy() {
        if (crime_map != null) {
            crime_map.onDestroy()
        }
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) {
            crime_map.onSaveInstanceState(outState)
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

    override fun returnCountry(country: String) {
        error_text.text = country
    }

}