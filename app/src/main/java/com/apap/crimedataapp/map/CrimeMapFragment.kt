package com.apap.crimedataapp.map

import android.app.Fragment
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.apap.crimedataapp.app.di.component.DaggerLocationComponent
import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.app.di.module.RepositoryModule
import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.presenter.LocationPresenter
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
import javax.inject.Inject


class CrimeMapFragment : Fragment(), LocationContract.View {

    @Inject
    protected lateinit var locationPresenter : LocationPresenter

    val component by lazy {  }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        inject()
        return inflater.inflate(R.layout.crime_map_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()

        crime_map.isClickable = true
        crime_map.mapScaleBar.isVisible = true

        val tc: TileCache = AndroidUtil.createTileCache(this.context, "mapcache", crime_map.model.displayModel.tileSize, 1f, crime_map.model.frameBufferModel.overdrawFactor)
        val mapDataStore = MapFile(File(Environment.getExternalStorageDirectory(), "world.map"))
        val tileRendererLayer = object : TileRendererLayer(tc, mapDataStore, crime_map.model.mapViewPosition, AndroidGraphicFactory.INSTANCE) {
            override fun onTap(tapLatLong: LatLong, layerXY: Point?, tapXY: Point?): Boolean {
                locationPresenter.getCountryForLocation(tapLatLong)
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
        //error_text.text = country
        Log.d("JSON", country)
    }

}