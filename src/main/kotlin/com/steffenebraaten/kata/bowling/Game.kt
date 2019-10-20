package com.steffenebraaten.kata.bowling

data class Game(val frames: List<Frame>)

interface GameParser {
    fun parse(frames: String): Game
}