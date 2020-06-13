package com.apap.crimedataapp.base

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.world_map_view.*

/**
 * Created by Adam on 2018-08-14.
 */
open class BaseMapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Mapbox.getInstance(this.activity, getString(R.string.access_token))
        return inflater.inflate(R.layout.world_map_view, container, false)
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
}