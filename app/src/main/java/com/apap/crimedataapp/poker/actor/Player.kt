package com.apap.crimedataapp.poker.actor

class Player(val name: String, val points: Int) {

    private var player : Player? = null

    fun createInstance(name: String, points: Int) : Player {
        if (getInstance() != null) {
            return getInstance() as Player
        }

        player = Player(name, points)
        return player as Player
    }

    fun getInstance() : Player? {
        return player
    }

    fun setPoints(newPoints: Int) {
        if (getInstance() != null) {
            player = Player(name, newPoints)
        }
    }
}