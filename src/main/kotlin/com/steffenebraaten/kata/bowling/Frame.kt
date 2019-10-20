package com.steffenebraaten.kata.bowling

const val ALL_PINS = 10
const val ZERO_PINS = 0

sealed class Frame {
    companion object
}

data class OpenFrame(val pinsFirstRoll: Int, val pinsSecondRoll: Int) : Frame()
data class Spare(val pinsFirstRoll: Int) : Frame()
object Strike : Frame()
data class Final(val frame: Frame, val firstExtraBall: Int? = null, val secondExtraBall: Int? = null) : Frame()

fun Frame.Companion.isStrikeBonusRoll(firstRoll: Int, secondRoll: Int? = null, thirdRoll: Int? = null) : Boolean = firstRoll == ALL_PINS && thirdRoll != null
fun Frame.Companion.isBonusRoll(firstRoll: Int, secondRoll: Int? = null, thirdRoll: Int? = null) : Boolean = thirdRoll != null
fun Frame.Companion.isStrike(firstRoll: Int) : Boolean = firstRoll == ALL_PINS
fun Frame.Companion.isSpare(firstRoll: Int, secondRoll: Int?) : Boolean = firstRoll + (secondRoll ?: ZERO_PINS) == ALL_PINS

fun Frame.Companion.fromRolls(firstRoll: Int, secondRoll: Int? = null, thirdRoll: Int? = null) : Frame =
    when {
        this.isStrikeBonusRoll(firstRoll, secondRoll, thirdRoll) ->
            Final(frame = Strike, firstExtraBall = secondRoll, secondExtraBall = thirdRoll)
        this.isBonusRoll(firstRoll, secondRoll, thirdRoll) ->
            Final(frame = fromRolls(firstRoll, secondRoll), firstExtraBall = thirdRoll)
        this.isStrike(firstRoll) -> Strike
        this.isSpare(firstRoll, secondRoll) -> Spare(firstRoll)
        else -> OpenFrame(firstRoll, secondRoll ?: ZERO_PINS)
    }


fun Frame.pinsOfFirstRoll(): Int =
    when (this) {
        is Strike -> ALL_PINS
        is Spare -> this.pinsFirstRoll
        is OpenFrame -> this.pinsFirstRoll
        is Final -> this.frame.pinsOfFirstRoll()
    }

fun Frame.totalOfPins(): Int =
    when (this) {
        is Strike -> ALL_PINS
        is Spare -> ALL_PINS
        is OpenFrame -> this.pinsFirstRoll + this.pinsSecondRoll
        is Final -> this.frame.totalOfPins() + (this.firstExtraBall ?: ZERO_PINS) + (this.secondExtraBall ?: ZERO_PINS)
    }