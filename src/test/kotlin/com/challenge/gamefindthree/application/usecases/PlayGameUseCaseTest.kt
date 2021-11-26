package com.challenge.gamefindthree.application.usecases

import com.challenge.gamefindthree.application.ports.outbound.GameEndedNotificationPort
import com.challenge.gamefindthree.application.ports.outbound.MoveCompletedNotificationPort
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PlayGameUseCaseTest {

    private lateinit var playGameUseCase: PlayGameUseCase

    @MockK
    private lateinit var gameEndedNotificationPort: GameEndedNotificationPort

    @MockK
    private lateinit var moveCompletedNotificationPort: MoveCompletedNotificationPort

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        playGameUseCase = PlayGameUseCase(gameEndedNotificationPort, moveCompletedNotificationPort)
    }


    @Test
    fun `When moving and it wins the game, it invokes port to notify the end of the game`() {
        val won = true
        val numberToWin = 3
        val resultingNumber = 1
        val noWinGame = false
        every { gameEndedNotificationPort.notify(won, noWinGame, resultingNumber) } just Runs

        playGameUseCase.move(numberToWin)

        verify(exactly = 1) { gameEndedNotificationPort.notify(won, noWinGame, resultingNumber) }
    }

    @Test
    fun `When moving and the game result is no-win game, it invokes port to notify the end of the game`() {
        val won = false
        val noWinGame = true
        val resultingNumber = 1
        val numberToCauseNoWinGame = 4
        every { gameEndedNotificationPort.notify(won, noWinGame, resultingNumber) } just Runs

        playGameUseCase.move(numberToCauseNoWinGame)

        verify(exactly = 1) { gameEndedNotificationPort.notify(won, noWinGame, resultingNumber) }
    }

    @Test
    fun `When moving and it does not win the game, it invokes port to notify the movement`() {
        val number = 56
        val addedNumber = 1
        val resultingNumber = 19
        every { moveCompletedNotificationPort.notify(any(), any()) } just Runs

        playGameUseCase.move(number)

        verify(exactly = 1) { moveCompletedNotificationPort.notify(addedNumber, resultingNumber) }
    }

    @Test
    fun `When and it does not win the game, it invokes port to notify the movement`() {
        val numberToWin = -9
        val addedNumber = 0
        val resultingNumber = -3
        every { moveCompletedNotificationPort.notify(any(), any()) } just Runs

        playGameUseCase.move(numberToWin)

        verify(exactly = 1) { moveCompletedNotificationPort.notify(addedNumber, resultingNumber) }
    }
}