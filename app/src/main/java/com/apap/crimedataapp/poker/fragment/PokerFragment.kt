package com.apap.crimedataapp.poker.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.apap.crimedataapp.poker.actor.Opponent
import com.apap.crimedataapp.poker.actor.Player
import com.apap.crimedataapp.poker.game.Dealer
import com.apap.crimedataapp.poker.game.Deck
import com.apap.crimedataapp.poker.game.Hand
import kotlinx.android.synthetic.main.poker_view.*

class PokerFragment : Fragment() {

    private lateinit var player: Player
    private lateinit var opponent: Opponent
    private lateinit var dealer: Dealer
    private lateinit var deck: Deck
    private lateinit var hand: Hand

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.poker_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player = Player.getInstance()!!
        player_name.text = player.name

        opponent = Opponent.getInstance()!!
        opponent_name.text = opponent.name

        dealer = Dealer.createInstance()

        deal_cards_button.setOnClickListener { v ->
            hand = dealer.dealCards()
        }
    }
}