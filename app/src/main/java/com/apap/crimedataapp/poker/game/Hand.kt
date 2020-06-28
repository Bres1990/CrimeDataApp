package com.apap.crimedataapp.poker.game

class Hand {

    companion object {
        private lateinit var cards : ArrayList<Card>

        fun createInstance() : Hand {

            cards = ArrayList()
            return Hand()
        }
    }

    fun add(card: Card) {
        cards.add(card)
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }

    fun isEmpty() : Boolean {

        return cards.isNullOrEmpty()
    }
}