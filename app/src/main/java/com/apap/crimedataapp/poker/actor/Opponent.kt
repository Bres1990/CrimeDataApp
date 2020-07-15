package com.apap.crimedataapp.poker.actor

class Opponent(val name: String, val points: Int) {

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

        fun dismiss() {
            opponent = null
        }
    }

    fun setRankingPoints(newPoints: Int) {
        if (getInstance() != null) {
            opponent = Opponent(getInstance()!!.name, newPoints)
        }
    }
}