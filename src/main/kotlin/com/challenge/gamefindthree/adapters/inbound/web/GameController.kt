package com.challenge.gamefindthree.adapters.inbound.web

import com.challenge.gamefindthree.application.ports.inbound.GameStartPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1")
class GameController(val gamePort: GameStartPort) {

    @Value("\${app.player}")
    private lateinit var player: String
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/start")
    fun start() {
        logger.info("#### Received request to start game manually! > player: $player")
        gamePort.start()
    }
}