package com.apap.crimedataapp.poker.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apap.crimedataapp.R
import com.apap.crimedataapp.app.PokerCivilizationsActivity
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import kotlinx.android.synthetic.main.poker_civ_navigation.*

class DrawDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(activity)
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    WorldMapFragment.fightMode = false
                    requireActivity().navigation.menu.getItem(2).isEnabled = false
                    requireActivity().navigation.menu.getItem(2).isChecked = false
                    requireActivity().navigation.menu.getItem(1).isChecked = true
                    requireActivity().fragmentManager.beginTransaction()
                            .replace(R.id.navigation_fragment, PokerCivilizationsActivity.mapFragment)
                            .commit()
                    dismiss()
                }
                .setMessage(getString(R.string.draw_dialog_text))
                .create()
    }

    companion object {
        const val TAG = "DRAW"

        @JvmStatic
        fun newInstance() : DrawDialog {
            return DrawDialog()
        }
    }
}