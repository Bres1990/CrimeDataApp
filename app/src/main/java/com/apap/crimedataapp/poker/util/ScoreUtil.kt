package com.apap.crimedataapp.poker.util

import com.apap.crimedataapp.poker.game.Card
import com.apap.crimedataapp.poker.game.Score
import com.apap.crimedataapp.poker.game.ScoreType
import com.apap.crimedataapp.poker.game.Suit

// TODO : Chain of Responsibility from detection functions
class ScoreUtil {

    companion object {
        private lateinit var cardScores: IntArray

        // FIXME Wrong scores are calculated
        fun countScore(finalHand: ArrayList<Card>): Score {
            println("Count score | cards: $finalHand")
            cardScores = IntArray(0)

            for (card in finalHand) {
                println("$card : ${card.score}")
                cardScores = cardScores.plus(card.score)
            }

            // min: 17000 max: 68000
            if (detectRoyalFlush(finalHand)) {
                return Score(ScoreType.ROYAL_FLUSH, 17000 * getColorValue(finalHand[0]))
            }

            // min: 4000 max: 16000
            if (detectStraightFlush(finalHand)) {
                return Score(ScoreType.STRAIGHT_FLUSH, 4000 * getColorValue(finalHand[0]))
            }

            // min: 2760 max: 3640
            if (detectFourOfKind()) {
                println("Four Of Kind | ${cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 4..4 }}")
                return Score(ScoreType.FOUR_OF_KIND, (4 * (cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 4..4 }!!.key) * 20) + 2600)
            }

            // min: 2100 max: 2630
            if (detectFullHouse()) {
                val three = cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 3..3 }!!.key
                println("Full House - three | $three")
                cardScores = cardScores.toList().minus(three).toIntArray()
                cardScores = cardScores.toList().minus(three).toIntArray()
                cardScores = cardScores.toList().minus(three).toIntArray()
                println("Full House - two | ${cardScores[0]}")
                return Score(ScoreType.FULL_HOUSE, (three * 3 * 10 + cardScores[0] * 2 * 10) + 2000)
            }

            // min: 1720 max: 1920
            if (detectFlush(finalHand)) {
                return Score(ScoreType.FLUSH, cardScores.sum() * getColorValue(finalHand[0]) + 1700)
            }

            // min: 1120 max: 1715
            if (detectStraight()) {
                return Score(ScoreType.STRAIGHT, cardScores.sum() * cardScores.toSet().max()!! + 1000)
            }

            // min: 1006 max: 1039
            if (detectThreeOfKind()) {
                println("Three Of Kind | ${cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 3..3}}")
                return Score(ScoreType.THREE_OF_KIND, 3 * (cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 3..3 }!!.key) + 1000)
            }

            // min: 150 max: 750
            if (detectTwoPairs()) {
                println("Detect Two Pairs | ${cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 2..2 }}")
                val pairOne = cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 2..2 }!!.key
                cardScores = cardScores.toList().minus(pairOne).toIntArray()
                cardScores = cardScores.toList().minus(pairOne).toIntArray()
                val pairTwo = cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 2..2 }!!.key
                return Score(ScoreType.TWO_PAIRS, (2 * pairOne + 2 * pairTwo) * 15)
            }

            // min: 20 max: 130
            if (detectPair()) {
                println("Detect Pair | ${cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 2..2 }}")
                return Score(ScoreType.PAIR, 2 * (cardScores.toList().groupingBy { it }.eachCount().maxBy { it.value in 2..2 }!!.key) * 5)
            }

            // min: 2 max: 13
            return Score(ScoreType.HIGHEST_CARD, cardScores.toSet().max()!!)
        }

        private fun detectPair(): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 2..2 }.isNotEmpty()
        }

        private fun detectTwoPairs(): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 2..2 }.size == 2
        }

        private fun detectThreeOfKind(): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 3..3 }.isNotEmpty()
        }

        private fun detectStraight(): Boolean {
            return hasConsecutiveValues()
        }

        private fun detectFlush(finalHand: ArrayList<Card>): Boolean {
            return hasSameColor(finalHand)
        }

        private fun detectFullHouse(): Boolean {
            return detectPair() && detectThreeOfKind()
        }

        private fun detectFourOfKind(): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value in 4..4 }.isNotEmpty()
        }

        private fun detectStraightFlush(finalHand: ArrayList<Card>): Boolean {
            return hasSameColor(finalHand) &&  hasConsecutiveValues()
        }

        private fun detectRoyalFlush(finalHand: ArrayList<Card>): Boolean {
            return hasSameColor(finalHand) && cardScores.sum() == 55
        }

        private fun hasConsecutiveValues(): Boolean {
            var previousScore = 0

            for (score in cardScores.toList().sortedDescending()) {
                if (previousScore == 0) {
                    previousScore = score
                    continue
                }

                if (previousScore - score != 1) {
                    return false
                }

                previousScore = score
            }

            return true
        }

        private fun hasSameColor(finalHand: ArrayList<Card>): Boolean {
            var suit: Suit? = null

            for (card in finalHand) {
                if (suit == null) {
                    suit = card.color
                }

                if (card.color != suit) {
                    return false
                }
            }
            return true
        }

        private fun getColorValue(card: Card): Int {
            if (card.name.contains("SPADES")) return 1
            if (card.name.contains("CLUBS")) return 2
            if (card.name.contains("DIAMONDS")) return 3
            if (card.name.contains("HEARTS")) return 4

            return 0
        }
    }
}