package com.apap.crimedataapp.map.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

class StateDetailsDialog constructor(val name: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setCancelable(false)
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