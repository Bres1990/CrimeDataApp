package com.apap.crimedataapp.poker.game

class Dealer {

    companion object {
        private var dealer: Dealer? = null
        private var deck: Deck = Deck.createInstance()

        fun createInstance() : Dealer {
            if (dealer != null) {
                return getInstance() as Dealer
            }

            dealer = Dealer()
            return dealer as Dealer
        }

        fun getInstance() : Dealer? {
            return dealer
        }
    }

    fun dealCards() : Hand {
        val hand = Hand()
        for (i in 0 until 5) {
            hand.add(deck.cards[i])
        }

        return hand
    }
}