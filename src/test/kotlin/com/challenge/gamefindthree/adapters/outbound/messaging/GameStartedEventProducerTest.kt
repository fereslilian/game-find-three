package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.GameStartedEvent
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
import kotlin.random.Random

@SpringBootTest
internal class GameStartedEventProducerTest {

    @Value("\${app.player}")
    private lateinit var player: String

    @Value("\${app.event.game-find-three.game_started_topic}")
    lateinit var topic: String

    @MockK
    private lateinit var kafkaTemplate: KafkaTemplate<String, GameStartedEvent?>

    @MockK
    private lateinit var kafkaTemplateReturn: ListenableFuture<SendResult<String, GameStartedEvent?>>

    @InjectMockKs
    private lateinit var gameStartedEventProducer: GameStartedEventProducer

    @BeforeEach
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When notifying a game started event, it calls kafka template with the right topic and message`() {
        val firstNumber = Random.nextInt()
        val gameStartedEvent = GameStartedEvent(player, firstNumber)
        every { kafkaTemplate.send(topic, gameStartedEvent) } returns kafkaTemplateReturn

        gameStartedEventProducer.notify(firstNumber)

        verify(exactly = 1) { kafkaTemplate.send(topic, gameStartedEvent) }
    }
}
