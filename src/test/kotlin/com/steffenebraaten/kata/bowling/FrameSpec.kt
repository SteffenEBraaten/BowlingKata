package com.steffenebraaten.kata.bowling

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it


object FrameSpec : Spek({

    given("a frame from rolls function") {

        it("should create a strike from one roll knocking down all pins") {
            Frame.fromRolls(10) `should equal` Strike
        }

        it("should create a spare from two rolls knocking down all pins") {
            Frame.fromRolls(1,9) `should equal` Spare(1)
        }

        it("should create an open frame from two rolls knocking down not all pins") {
            Frame.fromRolls(2,5) `should equal` OpenFrame(2, 5)
        }

        it("should create a tenth frame with a strike and two extra balls when first of three balls knocks down all pins") {
            Frame.fromRolls(10,10,10) `should equal` Final(Strike, 10, 10)
        }

        it("should parse a final frame with a spare and one extra ball when first and second of three balls knock down all pins") {
            Frame.fromRolls(5,5,10) `should equal` Final(Spare(5), 10 )
        }

    }
})

