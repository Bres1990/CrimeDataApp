package com.apap.crimedataapp.map.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.View
import com.apap.crimedataapp.R
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import com.apap.crimedataapp.poker.actor.Player
import kotlinx.android.synthetic.main.poker_civ_navigation.*
import kotlinx.android.synthetic.main.world_map_view.view.*

class StateChoiceDialog constructor(val name: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(activity)
                .setTitle(this.name)
                .setMessage(R.string.confirm_state_choice)
                .setCancelable(true)
                .setNegativeButton("NO") { dialog, _ -> dialog.cancel() }
                .setPositiveButton("YES") { _, _ ->
                    activity.navigation_fragment.state_bar.visibility = View.VISIBLE
                    activity.navigation_fragment.state_bar_state_name.text = this.name
                    activity.navigation_fragment.state_bar_points.text = "0"
                    WorldMapFragment.isStateChosen = true
                    Player.createInstance(this.name, 0)
                }.create()
    }

    companion object {
        const val TAG = "CHOOSE_STATE"

        @JvmStatic
        fun newInstance(state: String) : StateChoiceDialog {
            return StateChoiceDialog(state)
        }
    }
}