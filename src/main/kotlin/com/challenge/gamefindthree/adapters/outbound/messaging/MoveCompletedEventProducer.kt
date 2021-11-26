package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.MoveCompletedEvent
import com.challenge.gamefindthree.application.ports.outbound.MoveCompletedNotificationPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MoveCompletedEventProducer : MoveCompletedNotificationPort {

    @Value("\${app.player}")
    private lateinit var player: String

    @Value("\${app.event.game-find-three.move_completed_topic}")
    lateinit var topic: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, MoveCompletedEvent?>

    override fun notify(addedNumber: Int, resultingNumber: Int) {
        val message = createEvent(addedNumber, resultingNumber)
        Thread.sleep(2000)
        kafkaTemplate.send(topic, message)
        logger.info("#### Message: $message sent to topic: $topic")
    }

    private fun createEvent(addedNumber: Int, resultingNumber: Int) =
        MoveCompletedEvent(player, addedNumber, resultingNumber)
}