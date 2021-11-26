package com.challenge.gamefindthree.application.usecases

import com.challenge.gamefindthree.application.ports.outbound.GameStartedNotificationPort
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class StartGameUseCaseTest {

    private lateinit var startGameUseCase: StartGameUseCase

    @MockK
    private lateinit var gameStartedNotificationPort: GameStartedNotificationPort

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        startGameUseCase = StartGameUseCase(gameStartedNotificationPort)
    }

    @Test
    fun `When starting game, it invokes port to notify the start`() {
        every { gameStartedNotificationPort.notify(any()) } just Runs

        startGameUseCase.start()

        verify(exactly = 1) { gameStartedNotificationPort.notify(any()) }
    }
}