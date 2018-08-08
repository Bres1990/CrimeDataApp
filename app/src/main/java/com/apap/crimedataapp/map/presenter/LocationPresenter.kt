package com.apap.crimedataapp.map.presenter

import android.util.Log
import com.apap.crimedataapp.map.contract.LocationContract
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class LocationPresenter : LocationContract.Presenter {

    var subscriptions: CompositeDisposable
    var view: LocationContract.View

    @Inject constructor(view: LocationContract.View) {
        this.view = view

        subscriptions = CompositeDisposable()
    }

    override fun getCountryForLocation(coordinates: LatLng) {

        val geocoder: MapboxGeocoding = MapboxGeocoding.builder()
                .accessToken("pk.eyJ1IjoiYnJlczE5OTAiLCJhIjoiY2pramtqam9lMWJobDNwbzY2cW5iaDM0NyJ9.hrzimjh5-Sn0ENBsxQLRAQ")
                .query(Point.fromLngLat(coordinates.longitude, coordinates.latitude))
                .geocodingTypes(GeocodingCriteria.TYPE_REGION)
                .mode(GeocodingCriteria.MODE_PLACES)
                .build()

        geocoder.enqueueCall(object: Callback<GeocodingResponse> {
            override fun onFailure(call: Call<GeocodingResponse>?, t: Throwable?) {
                Timber.e("CrimeMapFragment", "Geocoding failure: " + t!!.message)
            }

            override fun onResponse(call: Call<GeocodingResponse>?, response: Response<GeocodingResponse>?) {
                val results: List<CarmenFeature> = response?.body()!!.features()
                if (results.isNotEmpty()) {
                    val feature: CarmenFeature = results[0]
                    view.returnCountry(feature.text()!!)
                    Log.v("CrimeMapFragment", "Carmen response: " + feature.toString())
                }
            }
        })
    }

    override fun dispose() {
        subscriptions.dispose()
        subscriptions.clear()
    }
}