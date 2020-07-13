package com.apap.crimedataapp.poker.game

class Hand {

    interface ChooseCards {
        fun addChosenCard(cardResName: String)
    }

    var chosenCards: List<Int> = ArrayList(2)

    companion object {
        private lateinit var cards: ArrayList<Card>

        fun createInstance(): Hand {

            cards = ArrayList()
            return Hand()
        }

        fun prepareHand(playerHand: Hand, communityCards: Hand?, index1: Int, index2: Int, index3: Int) : Hand {

            communityCards?.let {
                playerHand.add(communityCards.getCards()[index1])
                playerHand.add(communityCards.getCards()[index2])
                playerHand.add(communityCards.getCards()[index3])
            }

            return playerHand
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

    fun size(): Int {

        return if (isEmpty()) 0 else cards.size
    }

    fun manageChosenCards(cardIndex: Int) {
        if (cardIndex >= 0) {
            if (chosenCards.contains(cardIndex)) {
                chosenCards = chosenCards.minus(cardIndex)
                println("Removed card at index $cardIndex")
            } else if (chosenCards.size < 2) {
                chosenCards = chosenCards.plus(cardIndex)
                println("Added card at index $cardIndex")
            }
        }
    }
}