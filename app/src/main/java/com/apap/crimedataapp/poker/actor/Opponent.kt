package com.apap.crimedataapp.poker.actor

import com.apap.crimedataapp.poker.game.Card
import com.apap.crimedataapp.poker.game.Hand

class Opponent(val name: String, val points: Int) {

    var hand: Hand? = null

    companion object {
        private var opponent: Opponent? = null

        fun createInstance(name: String, points: Int): Opponent {
            if (opponent != null) {
                return getInstance() as Opponent
            }

            opponent = Opponent(name, points)
            return opponent as Opponent
        }

        fun getInstance(): Opponent? {
            return opponent
        }
    }

    fun getHandCards() : ArrayList<Card> {

        return Hand.getInstance("OPPONENT")!!.getCards()
    }

    fun setRankingPoints(newPoints: Int) {
        if (getInstance() != null) {
            opponent = Opponent(getInstance()!!.name, newPoints)
        }
    }
}