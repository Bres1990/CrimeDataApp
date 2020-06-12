package com.apap.crimedataapp.map.presenter

import android.util.Log
import com.apap.crimedataapp.map.contract.LocationContract
import com.mapbox.api.geocoding.v5.GeocodingCriteria
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class LocationPresenter : LocationContract.Presenter {

    private var subscriptions: CompositeDisposable
    var view: LocationContract.View

    @Inject constructor(view: LocationContract.View) {
        this.view = view

        subscriptions = CompositeDisposable()
    }

    override fun getStateForLocation(coordinates: LatLng) {

        val geocoder: MapboxGeocoding = getGeocoder(coordinates)

        geocoder.enqueueCall(object : Callback<GeocodingResponse> {
            override fun onFailure(call: Call<GeocodingResponse>?, t: Throwable?) {
                Timber.e(javaClass.simpleName, "Geocoding failure: " + t!!.message)
            }

            override fun onResponse(call: Call<GeocodingResponse>?, response: Response<GeocodingResponse>?) {
                val results: List<CarmenFeature> = response?.body()!!.features()
                if (results.isNotEmpty()) {
                    val feature: CarmenFeature = results[0]
                    view.returnState(feature.text()!!)
                    Log.v(javaClass.simpleName, "Carmen response: " + feature.toString())
                }
            }
        })
    }

    override fun getBoundsForState(feature: Feature) {

        val points = feature.geometry()!!.toJson()

        val regex = """.\d+\.\d+\,\d+\.\d+""".toRegex()

        val results: MutableList<String> = mutableListOf()

        var matchResult: MatchResult = regex.find(points)!!
        results.add(matchResult.value)

        while (matchResult.next() != null) {
            matchResult = matchResult.next()!!
            results.add(matchResult.value)
        }

        val coordinates: MutableList<LatLng> = mutableListOf()

        for (r in results) {
            val reg = """-\d+.\d+|\d+\.\d+""".toRegex()
            val matchRes = reg.find(r)
            val longitude = matchRes!!.value.toDouble()
            val latitude = matchRes.next()!!.value.toDouble()

            coordinates.add(LatLng(latitude, longitude))
        }

        Log.d(javaClass.simpleName, coordinates.toString())

        view.highlightBounds(coordinates)

    }

    override fun dispose() {
        subscriptions.dispose()
        subscriptions.clear()
    }

    private fun getGeocoder(coordinates: LatLng): MapboxGeocoding {
        return MapboxGeocoding.builder()
                .accessToken("pk.eyJ1IjoiYnJlczE5OTAiLCJhIjoiY2s3OTJwZHduMGw3ZDNsbnl5d3gxZ3FiMiJ9.2Q9x3rLq6CR2bqYRYMwlWg")
                .query(Point.fromLngLat(coordinates.longitude, coordinates.latitude))
                .geocodingTypes(GeocodingCriteria.TYPE_REGION)
                .mode(GeocodingCriteria.MODE_PLACES)
                .build()
    }
}