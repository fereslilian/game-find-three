package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.GameEndedEvent
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture

@SpringBootTest
internal class GameEndedEventProducerTest {

    @Value("\${app.player}")
    private lateinit var player: String

    @Value("\${app.event.game-find-three.game_ended_topic}")
    lateinit var topic: String

    @MockK
    private lateinit var kafkaTemplate: KafkaTemplate<String, GameEndedEvent?>

    @MockK
    private lateinit var kafkaTemplateReturn: ListenableFuture<SendResult<String, GameEndedEvent?>>

    @InjectMockKs
    private lateinit var gameEndedEventProducer: GameEndedEventProducer

    @BeforeEach
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When notifying a game ended event, it calls kafka template with the right topic and message`() {
        val won = true
        val noWinGame = false
        val resultingNumber = 57
        val gameEndedEvent = GameEndedEvent(player, won, noWinGame, resultingNumber)
        every { kafkaTemplate.send(topic, gameEndedEvent) } returns kafkaTemplateReturn

        gameEndedEventProducer.notify(won, noWinGame, resultingNumber)

        verify(exactly = 1) { kafkaTemplate.send(topic, gameEndedEvent) }
    }
}