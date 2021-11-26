package com.challenge.gamefindthree.adapters.inbound.messaging

import com.challenge.gamefindthree.GameStartedEvent
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random

@SpringBootTest
internal class GameStartedEventConsumerTest {

    @Value("\${app.player}")
    private lateinit var player: String

    @MockK
    private lateinit var gamePlayPort: GamePlayPort

    @InjectMockKs
    private lateinit var gameStartedEventConsumer: GameStartedEventConsumer

    @BeforeEach
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When listening a null message, it does not call the game play port`() {
        gameStartedEventConsumer.listen(null)

        verify { gamePlayPort wasNot Called }
    }

    @Test
    fun `When listening a message of this application player, it does not call the game play port`() {
        val number = Random.nextInt()
        val gameStartedEvent = GameStartedEvent(player, number)

        gameStartedEventConsumer.listen(gameStartedEvent)

        verify { gamePlayPort wasNot Called }
    }

    @Test
    fun `When listening a message of other application player, it calls the game play port`() {
        val secondPlayer = "second_player"
        val number = Random.nextInt()
        val gameStartedEvent = GameStartedEvent(secondPlayer, number)
        every { gamePlayPort.move(number) } just Runs

        gameStartedEventConsumer.listen(gameStartedEvent)

        verify(exactly = 1) { gamePlayPort.move(number) }
    }
}