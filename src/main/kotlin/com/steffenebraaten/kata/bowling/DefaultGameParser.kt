package com.steffenebraaten.kata.bowling

import java.lang.Character.getNumericValue

object DefaultGameParser : GameParser {

    private const val WHITE_SPACE = " "
    private const val MISS = "-"
    private const val STRIKE = "X"
    private const val SPARE = "/"
    private val allowedFrames= Regex("[-0-9X][-0-9X/]{0,2}")


    override fun parse(frames: String): Game {
        val allFrames = frames.split(WHITE_SPACE).map(::asFrame)
        return Game(allFrames)
    }

    fun asFrame(frame: String): Frame {
        require(allowedFrames.matches(frame)) { "Invalid frame '$frame'" }
        val firstRoll = frame[0].toNumberOfPins()
        val secondRoll = frame.getOrNull(1)?.toNumberOfPins(frame[0])
        val thirdRoll = frame.getOrNull(2)?.toNumberOfPins(frame.getOrNull(1))
        return Frame.fromRolls(firstRoll, secondRoll, thirdRoll)
    }

    private fun Char.toNumberOfPins(previous: Char? = null): Int {

        return when (this) {
            STRIKE.single() -> ALL_PINS
            MISS.single() -> ZERO_PINS
            SPARE.single() -> (ALL_PINS - (previous?.toNumberOfPins() ?: ZERO_PINS))
            else -> getNumericValue(this)
        }
    }
}