package com.challenge.gamefindthree.application.domain

import kotlin.random.Random

class Game {
    companion object {
        const val NUMBER_TO_WIN = 3
        const val MINIMUM_NUMBER_TO_PLAY = 2
        val POSSIBLE_NUMBERS_TO_ADD = listOf(-1, 0, 1)
    }

    fun start() = Random.nextInt()

    fun play(receivedNumber: Int): GameResult {
        val won = evaluateIfWon(receivedNumber)
        if (won) {
            return GameResult(won, 0, false, calculateResultingNumber(receivedNumber))
        }
        val noWinGame = evaluateIfNoWinGame(receivedNumber)
        if (noWinGame) {
            return GameResult(won, 0, noWinGame, calculateResultingNumber(receivedNumber))
        }
        val numberToAdd = findNumberToMakeDivisibleByThree(receivedNumber)
        val numberAfterAddition = receivedNumber + numberToAdd
        val resultingNumber = calculateResultingNumber(numberAfterAddition)

        return GameResult(
            won = won,
            addedNumber = numberToAdd,
            resultingNumber = resultingNumber,
            noWinGame = evaluateIfNoWinGame(resultingNumber)
        )
    }

    private fun findNumberToMakeDivisibleByThree(receivedNumber: Int) =
        POSSIBLE_NUMBERS_TO_ADD.first { (it + receivedNumber) % NUMBER_TO_WIN == 0 }

    private fun evaluateIfWon(resultingNumber: Int) = Math.abs(resultingNumber) == NUMBER_TO_WIN

    private fun calculateResultingNumber(number: Int) = (number / NUMBER_TO_WIN)

    private fun evaluateIfNoWinGame(resultingNumber: Int) = Math.abs(resultingNumber) < MINIMUM_NUMBER_TO_PLAY
}
