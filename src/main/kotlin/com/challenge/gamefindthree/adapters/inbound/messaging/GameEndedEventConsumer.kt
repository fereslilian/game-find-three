package com.challenge.gamefindthree.adapters.inbound.messaging

import com.challenge.gamefindthree.GameEndedEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GameEndedEventConsumer() {

    @Value("\${app.player}")
    private lateinit var player: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(
        topics = ["\${app.event.game-find-three.game_ended_topic}"],
        autoStartup = "\${app.event.auto-startup}",
    )
    fun listen(message: GameEndedEvent?) {
        if (shouldHandleEvent(message)) {
            if (message!!.won) {
                logger.info("#### Player $player won the game!!!!!!!!!!!")
            }else{
                logger.info("#### No-win game! Please, start again! resultingNumber: ${message.resultingNumber}")
            }
        }
    }

    private fun shouldHandleEvent(message: GameEndedEvent?) =
        message != null && message.player.toString() != player
}