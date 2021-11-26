package com.challenge.gamefindthree.application.domain

data class GameResult(
    val won: Boolean,
    val addedNumber: Int,
    val noWinGame: Boolean,
    val resultingNumber: Int
)
