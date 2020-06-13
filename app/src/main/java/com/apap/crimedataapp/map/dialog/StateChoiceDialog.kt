package com.apap.crimedataapp.map.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.apap.crimedataapp.R
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import kotlinx.android.synthetic.main.poker_civ_navigation.*
import kotlinx.android.synthetic.main.world_map_view.view.*

class StateChoiceDialog : android.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setMessage(R.string.confirm_state_choice)
                .setCancelable(true)
                .setNegativeButton("NO") { dialog, which -> dialog.cancel() }
                .setPositiveButton("YES") { dialog, which ->
                    activity.navigation_fragment.state_bar.visibility = View.VISIBLE
                    WorldMapFragment.isStateChosen = true
                }.create()
    }

    companion object {
        const val TAG = "CHOOSE_STATE"

        @JvmStatic
        fun newInstance(state: String) : StateChoiceDialog {
            val dialog = StateChoiceDialog()
            dialog.apply {
                arguments = Bundle().apply {
                    putString("state_name", state);
                }
            }

            return dialog
        }
    }
}