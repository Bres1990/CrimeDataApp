package com.apap.crimedataapp.poker.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.apap.crimedataapp.R
import com.apap.crimedataapp.app.PokerCivilizationsActivity
import com.apap.crimedataapp.map.fragment.WorldMapFragment
import com.apap.crimedataapp.poker.game.ScoreType
import kotlinx.android.synthetic.main.poker_civ_navigation.*

class WinnerDialog(private val score: ScoreType, private val winner: String) : DialogFragment() {

    // TODO : Display Winner State name
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
                .setMessage("$winner wins with ${score.name}")
                .create()
    }

    companion object {
        const val TAG = "WINNER"

        @JvmStatic
        fun newInstance(score: ScoreType, name: String) : WinnerDialog {
            return WinnerDialog(score, name)
        }
    }
}