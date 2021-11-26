package com.challenge.gamefindthree.adapters.outbound.messaging

import com.challenge.gamefindthree.GameEndedEvent
import com.challenge.gamefindthree.application.ports.outbound.GameEndedNotificationPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class GameEndedEventProducer : GameEndedNotificationPort {

    @Value("\${app.player}")
    private lateinit var player: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${app.event.game-find-three.game_ended_topic}")
    lateinit var topic: String

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, GameEndedEvent?>

    override fun notify(won: Boolean, noWinGame: Boolean, resultingNumber: Int) {
        val message = createEvent(won, noWinGame, resultingNumber)
        kafkaTemplate.send(topic, message)
        logger.info("#### Message: $message sent to topic: $topic")
    }

    private fun createEvent(won: Boolean, noWinGame: Boolean, resultingNumber: Int) =
        GameEndedEvent(player, won, noWinGame, resultingNumber)
}