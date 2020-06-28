package com.apap.crimedataapp.poker.actor

import com.apap.crimedataapp.poker.game.Hand

class Player(val name: String, var points: Int, var hand: Hand?) {

    companion object {
        private var player: Player? = null

        fun createInstance(name: String, points: Int) : Player {
            if (player != null) {
                return getInstance() as Player
            }

            player = Player(name, points, null)
            return player as Player
        }

        fun getInstance() : Player? {
            return player
        }
    }

    fun hasHandCards() : Boolean {
        return getHandCards() != null
    }

    fun getHandCards() : Hand? {
        return getInstance()!!.hand
    }

    fun setRankingPoints(newPoints: Int) {
        if (getInstance() != null) {
            player = Player(getInstance()!!.name, newPoints, getInstance()!!.hand)
        }
    }

}