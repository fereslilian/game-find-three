package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.MoveCompletedEvent
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
internal class MoveCompletedEventProducerTest {
    @Value("\${app.player}")
    private lateinit var player: String

    @Value("\${app.event.game-find-three.move_completed_topic}")
    lateinit var topic: String

    @MockK
    private lateinit var kafkaTemplate: KafkaTemplate<String, MoveCompletedEvent?>

    @MockK
    private lateinit var kafkaTemplateReturn: ListenableFuture<SendResult<String, MoveCompletedEvent?>>

    @InjectMockKs
    private lateinit var moveCompletedEventProducer: MoveCompletedEventProducer

    @BeforeEach
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When notifying a game ended event, it calls kafka template with the right topic and message`() {
        val addedNumber = 1
        val resultingNumber = 57
        val moveCompletedEvent = MoveCompletedEvent(player, addedNumber, resultingNumber)
        every { kafkaTemplate.send(topic, moveCompletedEvent) } returns kafkaTemplateReturn

        moveCompletedEventProducer.notify(addedNumber, resultingNumber)

        verify(exactly = 1) { kafkaTemplate.send(topic, moveCompletedEvent) }
    }
}