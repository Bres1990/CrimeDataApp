package com.apap.crimedataapp.poker.game

class Hand {

    companion object {
        private lateinit var cards : ArrayList<Card>
        var chosenCards : List<Int> = ArrayList(2)

        fun createInstance() : Hand {

            cards = ArrayList()
            return Hand()
        }

        fun addChosenCard(cardResName: String) {
            var index : Int = -1

            when (cardResName) {
                "hand_card_1" -> index = 0
                "hand_card_2" -> index = 1
                "hand_card_3" -> index = 2
                "hand_card_4" -> index = 3
                "hand_card_5" -> index = 4
            }

            if (index >= 0) {
                if (chosenCards.contains(index)) {
                    chosenCards = chosenCards.minus(index)
                    println("Removed card at index $index")
                } else if (chosenCards.size < 2) {
                    chosenCards = chosenCards.plus(index)
                    println("Added card at index $index")
                }
            }
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

    fun size() : Int {

        return if (isEmpty()) 0 else cards.size
    }
}