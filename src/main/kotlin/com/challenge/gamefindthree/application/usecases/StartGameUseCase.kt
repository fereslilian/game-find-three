package com.challenge.gamefindthree.application.usecases

import com.challenge.gamefindthree.application.domain.Game
import com.challenge.gamefindthree.application.ports.inbound.GameStartPort
import com.challenge.gamefindthree.application.ports.outbound.GameStartedNotificationPort
import org.slf4j.LoggerFactory

class StartGameUseCase(
    private val gameStartedNotificationPort: GameStartedNotificationPort,
) : GameStartPort {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun start() {
        logger.info("#### Starting game")
        val game = Game()
        val firstNumber = game.start()
        gameStartedNotificationPort.notify(firstNumber)
    }
}