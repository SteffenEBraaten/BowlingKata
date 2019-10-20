package com.steffenebraaten.kata.bowling

const val TEN = 10
const val ZERO = 0

fun scoreFrame(currentFrame: Frame, nextFrame: Frame?, nextOfTheFollowing: Frame?): Int {
    val (firstNextRoll, secondNextRoll) = getNextTwoRolls(nextFrame, nextOfTheFollowing)
    return when (currentFrame) {
        is Strike -> TEN + firstNextRoll + secondNextRoll
        is Spare -> TEN + firstNextRoll
        is OpenFrame, is Final -> currentFrame.totalOfPins()
    }
}

private fun getNextTwoRolls(nextFrame: Frame?, nextOfTheFollowing: Frame?): Pair<Int, Int> =
    when (nextFrame) {
        is Strike? -> Pair(TEN, nextOfTheFollowing?.pinsOfFirstRoll() ?: ZERO)
        is OpenFrame? -> Pair(nextFrame?.pinsFirstRoll ?: ZERO, nextFrame?.pinsSecondRoll ?: ZERO)
        is Spare? -> Pair(nextFrame?.pinsFirstRoll ?: ZERO, TEN - (nextFrame?.pinsFirstRoll ?: ZERO))
        is Final? -> getNextTwoRolls(nextFrame?.frame, Frame.fromRolls(nextFrame?.firstExtraBall ?: ZERO, ZERO))
        null -> Pair(ZERO, ZERO)
    }


fun score(game: Game): Int {
    require(game.frames.size == TEN) { "Invalid number of frames, was ${game.frames.size} and must be 10" }
    return calculateScore(game.frames)
}

fun calculateScore(frames: List<Frame>) : Int = frames.foldIndexed(0) { index: Int, currentScore: Int, frame: Frame ->
    currentScore + scoreFrame(frame, frames.getOrNull(index+1), frames.getOrNull(index+2))
}