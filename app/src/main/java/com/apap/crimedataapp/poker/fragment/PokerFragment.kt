package com.apap.crimedataapp.poker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.apap.crimedataapp.base.BaseMapFragment

class PokerFragment : BaseMapFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.poker_view, container, false)

        // TODO save chosen state and points in Player class
    }
}