package com.apap.crimedataapp.poker.game

import com.apap.crimedataapp.poker.actor.Opponent
import com.apap.crimedataapp.poker.actor.Player
import com.apap.crimedataapp.poker.util.ScoreUtil

class Dealer(private val scoreDisplay: ScoreDisplay) {

    // TODO move to Table
    interface ScoreDisplay {
        fun onDraw()
        fun onWinner(score: ScoreType, name: String)
    }

    companion object {
        private var dealer: Dealer? = null
        private var deck: Deck? = null

        fun createInstance(scoreDisplay: ScoreDisplay): Dealer {

            dealer = Dealer(scoreDisplay)
            deck = Deck.createInstance()
            return dealer as Dealer
        }

        private fun getInstance(): Dealer? {
            return dealer
        }
    }

    fun dealCards(): ArrayList<Card> {
        val hand = ArrayList<Card>()

        deck!!.shuffle()

        for (i in 0 until 5) {
            hand.add(deck!!.getCard(i))
        }

        println("Dealt cards: $hand")
        deck!!.remove(hand)

        return hand
    }

    fun determineWinner() {
        println("DETERMINE WINNER | Player hand: ${Table.playerCards}")
        println("DETERMINE WINNER | Opponent hand: ${Table.opponentCards}")

        val playerHand = Hand.getInstance("PLAYER")
        val opponentHand = Hand.getInstance("OPPONENT")

        val playerFinalHand = determineFinalHand(playerHand!!.chosenCards)
        val opponentFinalHand = determineFinalHand(opponentHand!!.chosenCards)

        println("Player final hand: $playerFinalHand")
        println("Opponent final hand: $opponentFinalHand")

        val playerScore = ScoreUtil.countScore(playerFinalHand)
        val opponentScore = ScoreUtil.countScore(opponentFinalHand)
        println("Player score: ${playerScore.value} | ${playerScore.type}, opponent score: ${opponentScore.value} | ${opponentScore.type}")

        if (playerScore.value == opponentScore.value) {
            scoreDisplay.onDraw()
        } else {
            // TODO update states territories
            val player = Player.getInstance()
            val opponent = Opponent.getInstance()

            if (playerScore.value > opponentScore.value) {
                player!!.setRankingPoints(player.points + 1)
                opponent!!.setRankingPoints(opponent.points - 1)
                scoreDisplay.onWinner(playerScore.type, "PLAYER")
            } else {
                // TODO keep track of points of each state
                opponent!!.setRankingPoints(opponent.points + 1)
                player!!.setRankingPoints(player.points - 1)
                scoreDisplay.onWinner(opponentScore.type, "OPPONENT")
            }
        }
    }

    private fun determineFinalHand(chosenCards: HashMap<Int, Card>): ArrayList<Card> {
        println("Chosen cards: $chosenCards")
        // check each possible 3 community cards for highest possible score

        val possibleHands = ArrayList<ArrayList<Card>>()
        possibleHands.add(Hand.prepareHand(chosenCards, 0, 1, 2))
        possibleHands.add(Hand.prepareHand(chosenCards, 0, 2, 3))
        possibleHands.add(Hand.prepareHand(chosenCards, 0, 1, 3))
        possibleHands.add(Hand.prepareHand(chosenCards, 0, 2, 4))
        possibleHands.add(Hand.prepareHand(chosenCards, 0, 3, 4))
        possibleHands.add(Hand.prepareHand(chosenCards, 1, 2, 3))
        possibleHands.add(Hand.prepareHand(chosenCards, 1, 2, 4))
        possibleHands.add(Hand.prepareHand(chosenCards, 1, 3, 4))
        possibleHands.add(Hand.prepareHand(chosenCards, 2, 3, 4))

        val possibleScores = HashMap<Int, ArrayList<Card>>()

        for (hand in possibleHands) {
            possibleScores.put(ScoreUtil.countScore(hand).value, hand)
        }

        val maxScore = possibleScores.keys.max()

        return possibleScores.get(maxScore)!!
    }

    fun getDeck(): Deck {
        return deck!!
    }
}