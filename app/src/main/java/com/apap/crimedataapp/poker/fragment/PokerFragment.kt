package com.apap.crimedataapp.poker.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apap.crimedataapp.R
import com.apap.crimedataapp.app.PokerCivilizationsActivity
import com.apap.crimedataapp.poker.actor.Opponent
import com.apap.crimedataapp.poker.actor.Player
import com.apap.crimedataapp.poker.dialog.BettingDialog
import com.apap.crimedataapp.poker.dialog.DrawDialog
import com.apap.crimedataapp.poker.dialog.WinnerDialog
import com.apap.crimedataapp.poker.game.Dealer
import com.apap.crimedataapp.poker.game.Hand
import com.apap.crimedataapp.poker.game.ScoreType
import com.apap.crimedataapp.poker.game.Table
import com.apap.crimedataapp.poker.game.Table.Companion.opponentCards
import com.apap.crimedataapp.poker.game.Table.Companion.playerCards
import com.apap.crimedataapp.poker.listener.OnCardClickListener
import kotlinx.android.synthetic.main.poker_view.*
import java.util.*

class PokerFragment : Fragment(), Dealer.ScoreDisplay, Hand.ChooseCards {

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

        dealer = Dealer.createInstance(this@PokerFragment)

        deal_cards_button.setOnClickListener { _ ->
            deal_cards_button.isEnabled = false
            deal_cards_button.visibility = View.INVISIBLE
            betting_button.isEnabled = true
            betting_button.visibility = View.VISIBLE

            Hand.createInstance("PLAYER")
            Hand.createInstance("OPPONENT")

            val playerHand = Hand.getInstance("PLAYER")

            if (playerHand!!.isEmpty()) {
                println("Dealing cards for Player")
                playerCards = dealer.dealCards()
                println("Dealing cards for Opponent")
                opponentCards = dealer.dealCards()
            }

            hand_card_1.setImageResource(this.resources.getIdentifier(playerCards[0].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_2.setImageResource(this.resources.getIdentifier(playerCards[1].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_3.setImageResource(this.resources.getIdentifier(playerCards[2].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_4.setImageResource(this.resources.getIdentifier(playerCards[3].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_5.setImageResource(this.resources.getIdentifier(playerCards[4].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        }

        betting_button.setOnClickListener { _ ->
            betting_button.isEnabled = false
            betting_button.visibility = View.INVISIBLE

            (activity as PokerCivilizationsActivity).showDialog(BettingDialog.newInstance(player.points > 0, player.points, opponent.points), BettingDialog.TAG)
        }

        choosing_button.setOnClickListener { _ ->
            choosing_button.isEnabled = false
            choosing_button.visibility = View.INVISIBLE
            confirm_choice_button.isEnabled = true
            confirm_choice_button.visibility = View.VISIBLE

            hand_card_1.setOnClickListener(OnCardClickListener(this@PokerFragment))
            hand_card_2.setOnClickListener(OnCardClickListener(this@PokerFragment))
            hand_card_3.setOnClickListener(OnCardClickListener(this@PokerFragment))
            hand_card_4.setOnClickListener(OnCardClickListener(this@PokerFragment))
            hand_card_5.setOnClickListener(OnCardClickListener(this@PokerFragment))
        }

        confirm_choice_button.setOnClickListener { _ ->
            val playerHand = Hand.getInstance("PLAYER")
            val opponentHand = Hand.getInstance("OPPONENT")

            if (playerHand!!.chosenCards.size == 2) {
                confirm_choice_button.isEnabled = false
                confirm_choice_button.visibility = View.INVISIBLE
                result_button.isEnabled = true
                result_button.visibility = View.VISIBLE
            }

            // TODO: create opponent cards choosing algorithm - take advantage of fun manageChosenCards()
            opponentHand!!.manageChosenCards(0, opponentCards)
            opponentHand.manageChosenCards(4, opponentCards)
        }

        result_button.setOnClickListener { _ ->
            // FIXME all hands == the last created hand instance

            dealer.determineWinner()
        }
    }

    override fun addChosenCard(cardResName: String) {
        var index: Int = -1

        when (cardResName) {
            "hand_card_1", "rival_card_1" -> index = 0
            "hand_card_2", "rival_card_2" -> index = 1
            "hand_card_3", "rival_card_3" -> index = 2
            "hand_card_4", "rival_card_4" -> index = 3
            "hand_card_5", "rival_card_5" -> index = 4
        }

        if (cardResName.startsWith("hand")) {

            Hand.getInstance("PLAYER")!!.manageChosenCards(index, playerCards)

        } else if (cardResName.startsWith("rival")) {

            Hand.getInstance("OPPONENT")!!.manageChosenCards(index, opponentCards)
        }

    }

    override fun onDraw() {
        (activity as PokerCivilizationsActivity).showDialog(DrawDialog.newInstance(), DrawDialog.TAG)
    }

    override fun onWinner(score: ScoreType, name: String) {
        (activity as PokerCivilizationsActivity).showDialog(WinnerDialog.newInstance(score, name), WinnerDialog.TAG)
    }

    fun showCommunityCards() {
        println("Dealing Community Cards")
        Table.communityCards = dealer.dealCards()

        dealer_card_1.setImageResource(this.resources.getIdentifier(Table.communityCards[0].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_2.setImageResource(this.resources.getIdentifier(Table.communityCards[1].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_3.setImageResource(this.resources.getIdentifier(Table.communityCards[2].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_4.setImageResource(this.resources.getIdentifier(Table.communityCards[3].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_5.setImageResource(this.resources.getIdentifier(Table.communityCards[4].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))

        choosing_button.isEnabled = true
        choosing_button.visibility = View.VISIBLE
    }

}