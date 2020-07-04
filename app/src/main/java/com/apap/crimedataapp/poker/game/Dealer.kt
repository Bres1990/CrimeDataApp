package com.apap.crimedataapp.poker.game

class Dealer {
    var communityCards: Hand? = null

    companion object {
        private var dealer: Dealer? = null
        private var deck: Deck? = null
        private var hand: Hand? = null

        fun createInstance() : Dealer {
            return if (dealer != null) {
                getInstance() as Dealer
            } else {
                dealer = Dealer()
                deck = Deck.createInstance()
                dealer as Dealer
            }
        }

        private fun getInstance() : Dealer? {
            return dealer
        }
    }

    fun dealCards() : Hand {
        hand = Hand.createInstance()

        if (hand!!.isEmpty()) {
            deck!!.shuffle()

            for (i in 0 until 5) {
                hand!!.add(deck!!.getCard(i))
            }

            deck!!.remove(hand!!.getCards())
        }

        return hand!!
    }

    fun determineWinner(playerHand: Hand, opponentHand: Hand) {

    }

    fun getDeck() : Deck {
        return deck!!
    }
}