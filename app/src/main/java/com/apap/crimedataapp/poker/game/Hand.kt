package com.apap.crimedataapp.poker.game

class Hand() {

    companion object {
        private var cards = ArrayList<Card>()
    }

    fun add(card: Card) {
        cards.add(card)
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }

    fun isEmpty() : Boolean {
        return cards.isEmpty()
    }
}