package com.apap.crimedataapp.map.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import com.apap.crimedataapp.R

class StateChoiceDialog : android.app.DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setMessage(R.string.confirm_state_choice)
                .setCancelable(true)
                .setNegativeButton("NO") { dialog, which -> dialog.cancel() }
                .setPositiveButton("YES") { dialog, which ->
                    // show country stats bar
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