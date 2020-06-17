package com.apap.crimedataapp.poker.game

class Deck constructor(val cards: ArrayList<Card>) {

    companion object {
        private var deck: Deck? = null

        /**
        @return a shuffled deck of cards
         **/
        fun createInstance() : Deck {
            var cards = ArrayList<Card>()
            cards.addAll(Card.values().asList())
            cards = ArrayList(cards.shuffled())

            deck = Deck(cards)
            return deck as Deck
        }
    }
}