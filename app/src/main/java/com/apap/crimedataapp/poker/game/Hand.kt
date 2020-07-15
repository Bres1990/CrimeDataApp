package com.apap.crimedataapp.poker.game

import com.apap.crimedataapp.poker.game.Table.Companion.communityCards

class Hand {

    interface ChooseCards {
        fun addChosenCard(cardResName: String)
    }

    var chosenCards: HashMap<Int, Card> = HashMap()

    companion object {
        private lateinit var cards: ArrayList<Card>
        private var instances: HashMap<String, Hand> = HashMap()

        fun createInstance(tag: String): Hand {
            val newInstance = Hand()
            instances.put(tag, newInstance)
            cards = ArrayList()
            return newInstance
        }

        fun getInstance(tag: String) : Hand? {

            return instances.get(tag)
        }

        fun prepareHand(chosenCards: HashMap<Int, Card>, index1: Int, index2: Int, index3: Int): ArrayList<Card> {

            val possibleHand = createInstance("RESULT")
            possibleHand.add(chosenCards.values.elementAt(0))
            possibleHand.add(chosenCards.values.elementAt(1))
            possibleHand.add(communityCards[index1])
            possibleHand.add(communityCards[index2])
            possibleHand.add(communityCards[index3])

            println("Possible hand: ${possibleHand.getCards()} | $index1 $index2 $index3")

            return possibleHand.getCards()
        }
    }

    fun add(card: Card) {
        cards.add(card)
    }

    fun getCards(): ArrayList<Card> {
        return cards
    }

    fun isEmpty(): Boolean {

        return cards.isNullOrEmpty()
    }

    fun manageChosenCards(cardIndex: Int, cards: ArrayList<Card>) {
        if (cardIndex >= 0) {
            if (chosenCards.contains(cardIndex)) {
                chosenCards.remove(cardIndex)
                println("Removed card at index $cardIndex")
            } else if (chosenCards.size < 2) {
                chosenCards.put(cardIndex, cards[cardIndex])
                println("Added card at index $cardIndex")
            }
        }
    }
}