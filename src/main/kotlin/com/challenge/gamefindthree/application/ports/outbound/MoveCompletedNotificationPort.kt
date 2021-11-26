package com.challenge.gamefindthree.application.ports.outbound

interface MoveCompletedNotificationPort {
    fun notify(addedNumber: Int, resultingNumber: Int)
}