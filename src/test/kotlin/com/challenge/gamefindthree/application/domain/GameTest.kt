package com.challenge.gamefindthree.application.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class GameTest {

    private val NUMBER_TO_WIN = 3

    @Test
    fun `When starting game, it returns an random int`() {
        val firstNumber: Int = Game().start()
        assertNotNull(firstNumber)
    }

    @Test
    fun `When playing with a number multiple of three, it returns the number divided by three and does not win`() {
        val expectedAddedNumber = 0
        val numberMultipleOfThree = 6
        val gameResult = Game().play(numberMultipleOfThree)

        assertEquals(false, gameResult.won)
        assertEquals(expectedAddedNumber, gameResult.addedNumber)
        assertEquals(numberMultipleOfThree / NUMBER_TO_WIN, gameResult.resultingNumber)
    }

    @Test
    fun `When playing with a number equals to three, it returns three as resulting number and won with true value`() {
        val number = 3
        val expectedAddedNumber = 0
        val gameResult = Game().play(number)

        assertEquals(true, gameResult.won)
        assertEquals(expectedAddedNumber, gameResult.addedNumber)
        assertEquals((number + expectedAddedNumber) / NUMBER_TO_WIN, gameResult.resultingNumber)
    }

    @Test
    fun `When playing with a number that plus one become multiple of three, it adds one`() {
        val number = 11
        val expectedAddedNumber = 1
        val gameResult = Game().play(number)

        assertEquals(false, gameResult.won)
        assertEquals(expectedAddedNumber, gameResult.addedNumber)
        assertEquals((number + expectedAddedNumber) / NUMBER_TO_WIN, gameResult.resultingNumber)
    }

    @Test
    fun `When playing with a number that minus one become multiple of three, it removes one`() {
        val number = 13
        val expectedAddedNumber = -1
        val gameResult = Game().play(number)

        assertEquals(false, gameResult.won)
        assertEquals(expectedAddedNumber, gameResult.addedNumber)
        assertEquals((number + expectedAddedNumber) / NUMBER_TO_WIN, gameResult.resultingNumber)
    }
}