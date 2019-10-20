package com.steffenebraaten.kata.bowling

class AmericanTenPinBowling(private val gameParser: GameParser = DefaultGameParser) {

    infix fun totalScore(frames: String): Int {
        val game: Game = gameParser.parse(frames)
        return score(game)
    }
}