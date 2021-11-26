package com.challenge.gamefindthree.adapters.inbound.messaging

import com.challenge.gamefindthree.MoveCompletedEvent
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MoveCompletedEventConsumerTest {

    @Value("\${app.player}")
    private lateinit var player: String

    @MockK
    private lateinit var gamePlayPort: GamePlayPort

    @InjectMockKs
    private lateinit var moveCompletedEventConsumer: MoveCompletedEventConsumer

    @BeforeEach
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When listening a null message, it does not call the game play port`() {
        moveCompletedEventConsumer.listen(null)

        verify { gamePlayPort wasNot Called }
    }

    @Test
    fun `When listening a message of this application player, it does not call the game play port`() {
        val addedNumber = 1
        val resultingNumber = 57
        val moveCompletedEvent = MoveCompletedEvent(player, addedNumber, resultingNumber)

        moveCompletedEventConsumer.listen(moveCompletedEvent)

        verify { gamePlayPort wasNot Called }
    }

    @Test
    fun `When listening a message of other application player, it calls the game play port`() {
        val secondPlayer = "second_player"
        val addedNumber = 1
        val resultingNumber = 57
        val moveCompletedEvent = MoveCompletedEvent(secondPlayer, addedNumber, resultingNumber)

        every { gamePlayPort.move(resultingNumber) } just Runs

        moveCompletedEventConsumer.listen(moveCompletedEvent)

        verify(exactly = 1) { gamePlayPort.move(resultingNumber) }
    }
}