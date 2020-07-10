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
import com.apap.crimedataapp.poker.listener.OnCardClickListener
import kotlinx.android.synthetic.main.poker_view.*
import java.util.*

class PokerFragment : Fragment(), Dealer.ScoreDisplay {

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

            if (!player.hasHandCards()) {
                player.hand = Hand.createInstance()
            }

            // FIXME Remember the dealt hands
            if (player.getHandCards()!!.isEmpty()) {
                player.hand = dealer.dealCards()
                opponent.hand = dealer.dealCards()
            }

            hand_card_1.setImageResource(this.resources.getIdentifier(player.hand!!.getCards()[0].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_2.setImageResource(this.resources.getIdentifier(player.hand!!.getCards()[1].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_3.setImageResource(this.resources.getIdentifier(player.hand!!.getCards()[2].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_4.setImageResource(this.resources.getIdentifier(player.hand!!.getCards()[3].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
            hand_card_5.setImageResource(this.resources.getIdentifier(player.hand!!.getCards()[4].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))

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

            hand_card_1.setOnClickListener(OnCardClickListener())
            hand_card_2.setOnClickListener(OnCardClickListener())
            hand_card_3.setOnClickListener(OnCardClickListener())
            hand_card_4.setOnClickListener(OnCardClickListener())
            hand_card_5.setOnClickListener(OnCardClickListener())
        }

        confirm_choice_button.setOnClickListener { _ ->
            if (Hand.chosenCards.size == 2) {
                confirm_choice_button.isEnabled = false
                confirm_choice_button.visibility = View.INVISIBLE
                result_button.isEnabled = true
                result_button.visibility = View.VISIBLE
            }
        }

        result_button.setOnClickListener { _ ->
            dealer.determineWinner(player.hand!!, opponent.hand!!)
        }
    }

    override fun onDraw() {
        (activity as PokerCivilizationsActivity).showDialog(DrawDialog.newInstance(), DrawDialog.TAG)
    }

    override fun onWinner(score: ScoreType) {
        (activity as PokerCivilizationsActivity).showDialog(WinnerDialog.newInstance(score), WinnerDialog.TAG)
    }

    fun showCommunityCards() {
        dealer.communityCards = dealer.dealCards()

        dealer_card_1.setImageResource(this.resources.getIdentifier(dealer.communityCards!!.getCards()[0].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_2.setImageResource(this.resources.getIdentifier(dealer.communityCards!!.getCards()[1].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_3.setImageResource(this.resources.getIdentifier(dealer.communityCards!!.getCards()[2].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_4.setImageResource(this.resources.getIdentifier(dealer.communityCards!!.getCards()[3].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))
        dealer_card_5.setImageResource(this.resources.getIdentifier(dealer.communityCards!!.getCards()[4].name.toLowerCase(Locale.ROOT), "drawable", activity.packageName))

        choosing_button.isEnabled = true
        choosing_button.visibility = View.VISIBLE
    }


}