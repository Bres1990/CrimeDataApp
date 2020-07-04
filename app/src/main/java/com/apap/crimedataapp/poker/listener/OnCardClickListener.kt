package com.apap.crimedataapp.poker.listener

import android.view.View
import com.apap.crimedataapp.poker.game.Hand

class OnCardClickListener : View.OnClickListener {

    var chosen = false

    override fun onClick(v: View?) {

        if (!chosen) {
            v!!.y = v.y.minus(50f)
            chosen = true
        } else  {
            v!!.y = v.y.plus(50f)
            chosen = false
        }

        Hand.addChosenCard(v.resources.getResourceEntryName(v.id))
    }
}