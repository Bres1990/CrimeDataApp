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
        world_map.onStart()
    }

    override fun onResume() {
        super.onResume()
        world_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        world_map.onPause()
    }

    override fun onStop() {
        super.onStop()
        world_map.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        world_map.onLowMemory()
    }

    override fun onDestroy() {
        if (world_map != null) {
            world_map.onDestroy()
        }
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) {
            world_map.onSaveInstanceState(outState)
        }
    }
}