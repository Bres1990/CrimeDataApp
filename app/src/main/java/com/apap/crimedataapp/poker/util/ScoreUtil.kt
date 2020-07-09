package com.apap.crimedataapp.poker.util

import com.apap.crimedataapp.poker.game.Card
import com.apap.crimedataapp.poker.game.Hand
import com.apap.crimedataapp.poker.game.Suit

class ScoreUtil {

    companion object {
        private lateinit var cardScores: IntArray

        //TODO return Score object with score type and value
        fun countScore(finalHand: Hand): Int {
            cardScores = IntArray(5)

            for (card in finalHand.getCards()) {
                cardScores.plus(card.score)
            }

            // min: 17000 max: 68000
            if (detectRoyalFlush(finalHand)) {
                return 17000 * getColorValue(finalHand.getCards()[0])
            }

            // min: 4000 max: 16000
            if (detectStraightFlush(finalHand)) {
                return 4000 * getColorValue(finalHand.getCards()[0])
            }

            // min: 2760 max: 3640
            if (detectFourOfKind(finalHand)) {
                return (4 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) * 20) + 2600
            }

            // min: 2100 max: 2630
            if (detectFullHouse(finalHand)) {
                val three = (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 })
                return (three[0]!! * 3 * 10 + cardScores.toSet().filterNot { it == three[0] }[0] * 2 * 10) + 2000
            }

            // min: 1720 max: 1920
            if (detectFlush(finalHand)) {
                return cardScores.sum() * getColorValue(finalHand.getCards()[0]) + 1700
            }

            // min: 1120 max: 1715
            if (detectStraight(finalHand)) {
                return cardScores.sum() * cardScores.toSet().max()!! + 1000
            }

            // min: 1006 max: 1039
            if (detectThreeOfKind(finalHand)) {
                return 3 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) + 1000
            }

            // min: 150 max: 750
            if (detectTwoPairs(finalHand)) {
                val pairOne = cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!
                val pairTwo = cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[1]!!
                return (2 * pairOne + 2 * pairTwo) * 15
            }

            // min: 20 max: 130
            if (detectPair(finalHand)) {
                return 2 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) * 5
            }

            // min: 2 max: 13
            return cardScores.toSet().max()!!
        }

        private fun detectPair(finalHand: Hand): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value == 2 }.isNotEmpty()
        }

        private fun detectTwoPairs(finalHand: Hand): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value == 2 }.size == 2
        }

        private fun detectThreeOfKind(finalHand: Hand): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value == 3 }.isNotEmpty()
        }

        private fun detectStraight(finalHand: Hand): Boolean {
            return hasConsecutiveValues()
        }

        private fun detectFlush(finalHand: Hand): Boolean {
            return hasSameColor(finalHand)
        }

        private fun detectFullHouse(finalHand: Hand): Boolean {
            return detectPair(finalHand) && detectThreeOfKind(finalHand)
        }

        private fun detectFourOfKind(finalHand: Hand): Boolean {
            return cardScores.toList().groupingBy { it }.eachCount().filter { it.value == 4 }.isNotEmpty()
        }

        private fun detectStraightFlush(finalHand: Hand): Boolean {
            return hasSameColor(finalHand) &&  hasConsecutiveValues()
        }

        private fun detectRoyalFlush(finalHand: Hand): Boolean {
            return hasSameColor(finalHand) && cardScores.sum() == 55
        }

        private fun hasConsecutiveValues(): Boolean {
            var previousScore = 0

            for (score in cardScores.toList().sortedDescending()) {
                if (previousScore == 0) {
                    previousScore = score
                    continue
                }

                if (previousScore - score > 1) {
                    return false
                }

                previousScore = score
            }

            return true
        }

        private fun hasSameColor(finalHand: Hand): Boolean {
            var suit: Suit? = null

            for (card in finalHand.getCards()) {
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