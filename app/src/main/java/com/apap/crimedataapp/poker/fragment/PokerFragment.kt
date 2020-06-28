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
import kotlinx.android.synthetic.main.poker_view.*
import java.util.*

class PokerFragment : Fragment() {

    private lateinit var player: Player
    private lateinit var opponent: Opponent
    private lateinit var dealer: Dealer

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

        deal_cards_button.setOnClickListener { _ ->
            deal_cards_button.isEnabled = false

            if (player.hand.isEmpty()) {
                player.hand = dealer.dealCards()
            }

            hand_card_1.setImageResource(this.resources.getIdentifier(player.hand.getCards()[0].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_2.setImageResource(this.resources.getIdentifier(player.hand.getCards()[1].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_3.setImageResource(this.resources.getIdentifier(player.hand.getCards()[2].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_4.setImageResource(this.resources.getIdentifier(player.hand.getCards()[3].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_5.setImageResource(this.resources.getIdentifier(player.hand.getCards()[4].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        }
    }
}