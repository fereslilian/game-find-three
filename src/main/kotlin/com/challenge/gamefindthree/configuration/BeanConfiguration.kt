package com.challenge.gamefindthree.configuration

import com.challenge.gamefindthree.adapters.outbound.messaging.GameEndedEventProducer
import com.challenge.gamefindthree.adapters.outbound.messaging.GameStartedEventProducer
import com.challenge.gamefindthree.adapters.outbound.messaging.MoveCompletedEventProducer
import com.challenge.gamefindthree.application.ports.inbound.GamePlayPort
import com.challenge.gamefindthree.application.ports.inbound.GameStartPort
import com.challenge.gamefindthree.application.ports.outbound.GameEndedNotificationPort
import com.challenge.gamefindthree.application.ports.outbound.GameStartedNotificationPort
import com.challenge.gamefindthree.application.ports.outbound.MoveCompletedNotificationPort
import com.challenge.gamefindthree.application.usecases.PlayGameUseCase
import com.challenge.gamefindthree.application.usecases.StartGameUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun gameStartPort(): GameStartPort = StartGameUseCase(gameStartedNotificationPort())

    @Bean
    fun gameEndedNotificationPort(): GameEndedNotificationPort = GameEndedEventProducer()

    @Bean
    fun gameStartedNotificationPort(): GameStartedNotificationPort = GameStartedEventProducer()

    @Bean
    fun moveCompletedNotificationPort(): MoveCompletedNotificationPort = MoveCompletedEventProducer()

    @Bean
    fun gamePlayPort(): GamePlayPort = PlayGameUseCase(gameEndedNotificationPort(), moveCompletedNotificationPort())
}
