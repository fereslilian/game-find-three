package com.challenge.gamefindthree.configuration

import com.challenge.gamefindthree.application.ports.inbound.GameStartPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener


@Configuration
class GameStartModeConfiguration(private val gamePort: GameStartPort) {

    @Value("\${app.player}")
    private lateinit var player: String

    @Value("\${app.start-mode}")
    private lateinit var startMode: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    private val automaticStartMode = "auto"

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun executeAfterApplicationIsReady() {
        logger.info("#### App started with player: $player startMode: $startMode groupId $groupId")
        if (startMode == automaticStartMode) {
            gamePort.start()
            return
        }
    }
}