package com.apap.crimedataapp.map.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.apap.crimedataapp.R
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.poker_civ_navigation.*

class StateDetailsDialog constructor(val name: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val play = Button(activity)
        play.text = getString(R.string.poker_fight)
        play.setOnClickListener {
            WorldMapFragment.fightMode = true
            WorldMapFragment.fightState = name
            dismiss()
        }

        return AlertDialog.Builder(activity)
                .setCancelable(false)
                .setView(play)
                .setTitle(name)
                .setNegativeButton("CONFIRM") {
                    dialog, which ->  dialog.cancel()
                }
                .create()
    }

    companion object {
        const val TAG  = "STATE_DETAILS"

        @JvmStatic
        fun newInstance(state: String) : StateDetailsDialog {
            return StateDetailsDialog(state)
        }
    }
}