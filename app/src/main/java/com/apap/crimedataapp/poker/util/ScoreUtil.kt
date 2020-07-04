package com.apap.crimedataapp.poker.util

import com.apap.crimedataapp.poker.game.Card
import com.apap.crimedataapp.poker.game.Hand

class ScoreUtil {

    companion object {
        fun countScore(finalHand: Hand): Int {
            val cardScores = IntArray(5)

            for (card in finalHand.getCards()) {
                cardScores.plus(card.score)
            }

            // min: 17000 max: 68000
            if (detectRoyalFlush()) {
                return 17000 * getColorValue(finalHand.getCards()[0])
            }

            // min: 4000 max: 16000
            if (detectStraightFlush()) {
                return 4000 * getColorValue(finalHand.getCards()[0])
            }

            // min: 2760 max: 3640
            if (detectFourOfKind()) {
                return (4 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) * 20) + 2600
            }

            // min: 2100 max: 2630
            if (detectFullHouse()) {
                val three = (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 })
                return (three[0]!! * 3 * 10 + cardScores.toSet().filterNot { it == three[0] }[0] * 2 * 10) + 2000
            }

            // min: 1720 max: 1920
            if (detectFlush()) {
                return cardScores.sum() * getColorValue(finalHand.getCards()[0]) + 1700
            }

            // min: 1120 max: 1715
            if (detectStraight()) {
                return cardScores.sum() * cardScores.toSet().max()!! + 1000
            }

            // min: 1006 max: 1039
            if (detectThreeOfKind()) {
                return 3 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) + 1000
            }

            // min: 150 max: 750
            if (detectTwoPairs()) {
                val pairOne = cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!
                val pairTwo = cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[1]!!
                return (2 * pairOne + 2 * pairTwo) * 15
            }

            // min: 20 max: 130
            if (detectPair()) {
                return 2 * (cardScores.toList().groupingBy { it }.eachCount().filter { it.value > 1 }[0]!!) * 5
            }

            // min: 2 max: 13
            return cardScores.toSet().max()!!
        }

        private fun detectPair(): Boolean {
            return false
        }

        private fun detectTwoPairs(): Boolean {
            return false
        }

        private fun detectThreeOfKind(): Boolean {
            return false
        }

        private fun detectStraight(): Boolean {
            return false
        }

        private fun detectFlush(): Boolean {
            return false
        }

        private fun detectFullHouse(): Boolean {
            return false
        }

        private fun detectFourOfKind(): Boolean {
            return false
        }

        private fun detectStraightFlush(): Boolean {
            return false
        }

        private fun detectRoyalFlush(): Boolean {
            return false
        }

        private fun getColorValue(card: Card): Int {
            if (card.name.contains("SPADES")) return 1
            if (card.name.contains("CLUBS")) return 2
            if (card.name.contains("DIAMONDS")) return 3
            if (card.name.contains("HEARTS")) return 4

            return 1
        }
    }
}