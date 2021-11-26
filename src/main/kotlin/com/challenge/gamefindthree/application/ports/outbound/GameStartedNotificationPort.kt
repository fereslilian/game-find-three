package com.challenge.gamefindthree.application.ports.outbound

interface GameStartedNotificationPort {
    fun notify(number: Int)
}