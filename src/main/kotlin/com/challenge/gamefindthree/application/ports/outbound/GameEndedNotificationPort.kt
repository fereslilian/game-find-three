package com.challenge.gamefindthree.application.ports.outbound

interface GameEndedNotificationPort {
    fun notify(won: Boolean, noWinGame: Boolean, resultingNumber: Int)
}