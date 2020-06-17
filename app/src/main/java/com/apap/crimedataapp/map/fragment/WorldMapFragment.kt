package com.apap.crimedataapp.map.fragment

import android.os.Bundle
import android.view.View
import com.apap.crimedataapp.app.di.component.DaggerLocationComponent
import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.app.di.module.RepositoryModule
import com.apap.crimedataapp.base.BaseMapFragment
import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.dialog.StateChoiceDialog
import com.apap.crimedataapp.map.dialog.StateDetailsDialog
import com.apap.crimedataapp.map.presenter.LocationPresenter
import com.apap.crimedataapp.poker.actor.Player
import com.google.android.material.snackbar.Snackbar
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.world_map_view.*
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject


class WorldMapFragment : BaseMapFragment(), LocationContract.View {

    @Inject
    protected lateinit var locationPresenter: LocationPresenter

    lateinit var map: MapboxMap

    companion object {
        var isStateChosen = false
        var chosenState = ""
        var fightMode = false
        var fightState = ""
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        world_map.onCreate(savedInstanceState)
        inject()

        world_map.isClickable = true

        world_map.getMapAsync { mapboxMap ->
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
        }
    }

    override fun onResume() {
        super.onResume()

        if (isStateChosen) {
            val player = Player.getInstance()
            state_bar_state_name.text = player!!.name
            state_bar_points.text = player.points.toString()
        }

        if (fightMode) {
            view.snack("Fight against $fightState", Snackbar.LENGTH_LONG)
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
        chosen_state_name.text = state
        if (!isStateChosen) {
            locationPresenter.chooseState(state)
        } else {
            displayStateDetailsDialog(state)
        }
    }

    override fun displayStateChoiceDialog(state: String) {

        val fm = activity.fragmentManager
        val transaction = fm.beginTransaction()
        val previous = fm.findFragmentByTag(StateChoiceDialog.TAG)
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)
        transaction.commit()

        val dialogFragment = StateChoiceDialog.newInstance(state)
        dialogFragment.show(fragmentManager, StateChoiceDialog.TAG)
    }


    fun displayStateDetailsDialog(state: String) {

        val fm = activity.fragmentManager
        val transaction = fm.beginTransaction()
        val previous = fm.findFragmentByTag(StateChoiceDialog.TAG)
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)
        transaction.commit()

        val dialogFragment = StateDetailsDialog.newInstance(state)
        dialogFragment.show(fragmentManager, StateDetailsDialog.TAG)
    }

    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }


}