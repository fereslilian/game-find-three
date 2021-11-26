package com.challenge.gamefindthree.adapters.inbound.messaging

import com.challenge.gamefindthree.GameStartedEvent
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GameStartedEventConsumer(private val gamePort: GamePlayPort) {

    @Value("\${app.player}")
    private lateinit var player: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(
        topics = ["\${app.event.game-find-three.game_started_topic}"],
        autoStartup = "\${app.event.auto-startup}",
    )
    fun listen(message: GameStartedEvent?) {
        if (shouldHandleEvent(message)) {
            logger.info("#### Handling event on GameStartedEventConsumer $message")
            gamePort.move(message!!.number)
        }
    }

    private fun shouldHandleEvent(message: GameStartedEvent?) =
        message != null && message.player.toString() != player
}