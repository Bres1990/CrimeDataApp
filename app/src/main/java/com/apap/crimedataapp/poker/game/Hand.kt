package com.apap.crimedataapp.poker.game

class Hand() {

    companion object {
        private var hand: Hand? = null
        lateinit var cards: ArrayList<Card>
    }

    fun add(card: Card) {
        cards.add(card)
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }
}