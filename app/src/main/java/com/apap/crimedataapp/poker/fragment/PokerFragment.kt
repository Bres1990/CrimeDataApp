package com.apap.crimedataapp.poker.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.apap.crimedataapp.poker.actor.Opponent
import com.apap.crimedataapp.poker.actor.Player
import kotlinx.android.synthetic.main.poker_view.*

class PokerFragment : Fragment() {

    private lateinit var player: Player
    private lateinit var opponent: Opponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.poker_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = Player.getInstance()!!
        player_name.text = player.name

        opponent = Opponent.getInstance()!!
        opponent_name.text = opponent.name
    }
}