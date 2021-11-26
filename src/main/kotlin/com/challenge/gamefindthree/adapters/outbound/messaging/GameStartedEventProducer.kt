package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.GameStartedEvent
import com.challenge.gamefindthree.application.ports.outbound.GameStartedNotificationPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class GameStartedEventProducer : GameStartedNotificationPort {

    @Value("\${app.player}")
    private lateinit var player: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${app.event.game-find-three.game_started_topic}")
    lateinit var topic: String

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, GameStartedEvent?>

    override fun notify(number: Int) {
        val message = createEvent(number)
        kafkaTemplate.send(topic, message)
        logger.info("#### Message: $message sent to topic: $topic")
    }

    private fun createEvent(number: Int) =
        GameStartedEvent(player, number)
}