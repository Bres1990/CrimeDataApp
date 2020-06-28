package com.apap.crimedataapp.poker.game

class Deck {

    companion object {
        private lateinit var cards: ArrayList<Card>

        /**
        @return a shuffled deck of cards
         **/
        fun createInstance() : Deck {
            cards = ArrayList()
            cards.addAll(Card.values().asList())
            cards = ArrayList(cards.shuffled())

            return Deck()
        }
    }

    override fun toString(): String {
        var string = ""
        for (i in 0 until cards.size) {
            string += "${cards[i].name} "
        }

        return string
    }

    fun getCard(index: Int) : Card {
        return cards[index]
    }

    fun getSize() : Int {
        return cards.size
    }

    fun remove(list: ArrayList<Card>) {
        for (i in 0 until list.size) {
            cards.remove(list[i])
        }
    }

    fun shuffle() {
        cards = ArrayList(cards.shuffled())
    }
}