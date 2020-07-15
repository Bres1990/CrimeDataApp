package com.apap.crimedataapp.poker.actor

import com.apap.crimedataapp.poker.game.Card
import com.apap.crimedataapp.poker.game.Hand

class Player(val name: String, var points: Int) {

    companion object {
        private var player: Player? = null

        fun createInstance(name: String, points: Int) : Player {
            if (player != null) {
                return getInstance() as Player
            }

            player = Player(name, points)
            return player as Player
        }

        fun getInstance() : Player? {
            return player
        }
    }

    fun getHandCards() : ArrayList<Card>? {
        return Hand.getInstance("PLAYER")!!.getCards()
    }

    fun setRankingPoints(newPoints: Int) {
        if (getInstance() != null) {
            player = Player(getInstance()!!.name, newPoints)
        }
    }

}