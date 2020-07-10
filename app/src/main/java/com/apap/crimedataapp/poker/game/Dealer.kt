package com.apap.crimedataapp.poker.game

import com.apap.crimedataapp.poker.util.ScoreUtil

class Dealer(private val scoreDisplay: ScoreDisplay) {

    // TODO move to Table
    interface ScoreDisplay {
        fun onDraw()
        fun onWinner(score: ScoreType)
    }

    var communityCards: Hand? = null

    companion object {
        private var dealer: Dealer? = null
        private var deck: Deck? = null
        private var hand: Hand? = null

        fun createInstance(scoreDisplay: ScoreDisplay) : Dealer {
            return if (dealer != null) {
                getInstance() as Dealer
            } else {
                dealer = Dealer(scoreDisplay)
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
        val playerFinalHand = determineFinalHand(playerHand.chosenCards)
        val opponentFinalHand = determineFinalHand(opponentHand.chosenCards)

        val playerScore = ScoreUtil.countScore(playerFinalHand)
        val opponentScore = ScoreUtil.countScore(opponentFinalHand)

        if (playerScore.value == opponentScore.value) {
            scoreDisplay.onDraw()
        } else {
            if (playerScore.value > opponentScore.value) {
                scoreDisplay.onWinner(playerScore.type)
            } else {
                scoreDisplay.onWinner(opponentScore.type)
            }
        }
    }

    fun determineFinalHand(chosenCards: List<Int>) : Hand {
        val finalHand = Hand.createInstance()



        return finalHand
    }

    fun getDeck() : Deck {
        return deck!!
    }
}