package com.apap.crimedataapp.poker.game

class Dealer {

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

    // FIXME always returns the same cards to everyone
    fun dealCards() : Hand {
        println(deck!!.getSize())
        println(deck!!.toString())
        hand = Hand.createInstance()

        if (hand!!.isEmpty()) {
            deck!!.shuffle()

            for (i in 0 until 5) {
                hand!!.add(deck!!.getCard(i))
            }

            deck!!.remove(hand!!.getCards())
            println(deck!!.getSize())
            println(deck!!.toString())
        } else println("Dealing cards but hand not empty")

        return hand!!
    }

    fun getDeck() : Deck {
        return deck!!
    }
}