package com.challenge.gamefindthree.application.usecases

import com.challenge.gamefindthree.application.domain.Game
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import com.challenge.gamefindthree.application.ports.outbound.GameEndedNotificationPort
import com.challenge.gamefindthree.application.ports.outbound.MoveCompletedNotificationPort
import org.slf4j.LoggerFactory

class PlayGameUseCase(
    private val gameEndedNotificationPort: GameEndedNotificationPort,
    private val moveCompletedNotificationPort: MoveCompletedNotificationPort
) : GamePlayPort {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun move(number: Int) {
        logger.info("#### Moving with number: $number")
        val game = Game()
        val gameResult = game.play(number)

        if (gameResult.won || gameResult.noWinGame) {
            gameEndedNotificationPort.notify(gameResult.won, gameResult.noWinGame, gameResult.resultingNumber)
        } else {
            moveCompletedNotificationPort.notify(gameResult.addedNumber, gameResult.resultingNumber)
        }
    }
}