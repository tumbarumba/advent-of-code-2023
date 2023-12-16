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

    @Test
    fun testParseLine() {
        val actual = day02.parseLine("Game 1: 9 red, 5 blue, 6 green; 6 red, 13 blue; 2 blue, 7 green, 5 red")
        val expected = Game(1, listOf(
            GameSample(9, 6, 5),
            GameSample(6, 0, 13),
            GameSample(5, 7, 2)))
        assertEquals(expected, actual)
    }

    @Test
    fun testParseSample() {
        assertEquals(GameSample(9, 6, 5), day02.parseSample("9 red, 5 blue, 6 green"))
        assertEquals(GameSample(6, 0, 13), day02.parseSample("6 red, 13 blue"))
        assertEquals(GameSample(5, 7, 2), day02.parseSample("2 blue, 7 green, 5 red"))
    }
}
