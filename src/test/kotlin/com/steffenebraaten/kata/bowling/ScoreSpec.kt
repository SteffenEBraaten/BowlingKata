package com.steffenebraaten.kata.bowling

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.`with message`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

object ScoreParserSpec : Spek({

    given("a single frame score calculation") {

        val scoreFrame: (Frame, Frame?, Frame?) -> Int = ::scoreFrame

        it("should score an open frame") {
            scoreFrame(OpenFrame(1, 6), null, null) `should be equal to` 7
        }

        it("should score a strike when the next two rolls form a spare") {
            scoreFrame(Strike, Spare(1), null) `should be equal to` 20
        }

        it("should score a strike when the next two rolls are strikes too") {
            scoreFrame(Strike, Strike, Strike) `should be equal to` 30
        }

        it("should score a strike when the next two rolls are strikes in the tenth frame") {
            scoreFrame(Strike, Final(Strike,10,2), null) `should be equal to` 30
        }

        it("should score a strike when the next two rolls form a spare in the tenth frame") {
            scoreFrame(Strike, Final(Spare(2),1,null), null) `should be equal to` 20
        }

        it("should score the tenth frame with an strike when the both extra balls know down all the pins") {
            scoreFrame(Final(Strike,10,10), null, null) `should be equal to` 30
        }

        it("should score the tenth frame when extra balls are a spare") {
            scoreFrame(Final(Strike,1,9), null, null) `should be equal to` 20
        }

        it("should score spare when the next roll knocks down to pins") {
            scoreFrame(Spare(2), OpenFrame(1, 6), Strike) `should be equal to` 11
        }

        it("should score spare when the next roll knocks down all the pins") {
            scoreFrame(Spare(2), Strike, Strike) `should be equal to` 20
        }

    }

    given("a full game score function") {

        val score: (Game) -> Int = ::score

        it("should fail trying to score a game with invalid number of frames") {
            val game = Game(listOf(Strike))
            val func = { score(game) }
            func `should throw` IllegalArgumentException::class `with message` "Invalid number of frames, was 1 and must be 10"

        }

        it("should score a game with 12 strikes in a row") {
            val game = Game((1..9).toList().map { Strike } + Final(Strike,10,10))
            score(game) `should be equal to` 300

        }

        it("should score a game with 10 pairs of 5 and spare and a final 5") {
            val game = Game((1..9).toList().map { Spare(5) } + Final(Spare(5),5))
            score(game) `should be equal to` 150

        }

        it("should score an example of complete game") {
            val game = Game(
                listOf(
                    Strike,
                    Spare(7),
                    OpenFrame(9,0),
                    Strike,
                    OpenFrame(0, 8),
                    Spare(8),
                    OpenFrame(0, 6),
                    Strike,
                    Strike,
                    Final(Strike,8,1)
                ))
            score(game) `should be equal to` 167
        }
    }
})