package com.challenge.gamefindthree.adapters.inbound.messaging

import com.challenge.gamefindthree.MoveCompletedEvent
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class MoveCompletedEventConsumer(private val gamePort: GamePlayPort) {

    @Value("\${app.player}")
    private lateinit var player: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(
        topics = ["\${app.event.game-find-three.move_completed_topic}"],
        autoStartup = "\${app.event.auto-startup}",
    )
    fun listen(message: MoveCompletedEvent?) {
        if (shouldHandleEvent(message)) {
            logger.info("#### Handling event on MoveCompletedEventConsumer $message")
            gamePort.move(message!!.resultingNumber)
        }
    }

    private fun shouldHandleEvent(message: MoveCompletedEvent?) =
        message != null && message.player.toString() != player
}