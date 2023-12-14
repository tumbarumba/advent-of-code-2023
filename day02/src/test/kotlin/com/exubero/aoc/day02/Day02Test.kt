package com.exubero.aoc.day02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day02Test {
    private val day02 = Day02()

    @Test
    fun testLoadInputData() {
        val inputData = day02.loadInputData()
        assertEquals("Game 1: 9 red, 5 blue, 6 green; 6 red, 13 blue; 2 blue, 7 green, 5 red", inputData.first())
        assertEquals("Game 100: 5 blue, 2 green, 7 red; 14 red, 15 green, 1 blue; 3 blue, 3 red; 8 green, 10 red, 6 blue; 6 blue, 4 red, 8 green", inputData.last())
    }
}