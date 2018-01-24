package com.apap.crimedataapp

import android.app.Fragment
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.crime_map_view.*
import org.mapsforge.core.model.LatLong
import org.mapsforge.core.model.Point
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.InternalRenderTheme
import java.io.File


class CrimeMapFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.crime_map_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crime_map.isClickable = true
        crime_map.mapScaleBar.isVisible = true

        val tc: TileCache = AndroidUtil.createTileCache(this.context, "mapcache", crime_map.model.displayModel.tileSize, 1f, crime_map.model.frameBufferModel.overdrawFactor)
        val mapDataStore = MapFile(File(Environment.getExternalStorageDirectory(), "world.map"))
        val tileRendererLayer = object : TileRendererLayer(tc, mapDataStore, crime_map.model.mapViewPosition, AndroidGraphicFactory.INSTANCE) {
            override fun onTap(tapLatLong: LatLong?, layerXY: Point?, tapXY: Point?): Boolean {
                val latitude = tapLatLong!!.latitude.toString()
                val longitude = tapLatLong.longitude.toString()
                val coordinates = latitude + " " + longitude
                Log.i("Coordinates", coordinates)
                error_text.text = coordinates

                // TODO: get country from coordinates (https://nominatim.openstreetmap.org/reverse?format=json&lat=51.0&lon=17.0)

                return true
            }
        }
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER)
        crime_map.layerManager.layers.add(tileRendererLayer)

        crime_map.setZoomLevel(1)
        crime_map.setZoomLevelMax(3)
        crime_map.invalidate()
    }

    override fun onDestroy() {
        if (crime_map != null) {
            crime_map.destroyAll()
            AndroidGraphicFactory.clearResourceMemoryCache()
        }
        super.onDestroy()
    }
}