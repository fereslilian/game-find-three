package com.challenge.gamefindthree

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class GameFindThreeApplication

fun main(args: Array<String>) {
    runApplication<GameFindThreeApplication>(*args)
}
