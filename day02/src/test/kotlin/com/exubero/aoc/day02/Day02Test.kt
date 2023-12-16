package com.exubero.aoc.day02

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day02Test {
    private val gameBuilder = GameBuilder()

    @Test
    fun testLoadInputData() {
        val inputData = Day02.loadInputData()
        assertEquals("Game 1: 9 red, 5 blue, 6 green; 6 red, 13 blue; 2 blue, 7 green, 5 red", inputData.first())
        assertEquals("Game 100: 5 blue, 2 green, 7 red; 14 red, 15 green, 1 blue; 3 blue, 3 red; 8 green, 10 red, 6 blue; 6 blue, 4 red, 8 green", inputData.last())
    }

    @Test
    fun testParseLine() {
        val actual = gameBuilder.parseLine("Game 1: 9 red, 5 blue, 6 green; 6 red, 13 blue; 2 blue, 7 green, 5 red")
        val expected = Game(1, listOf(
            ColourSet(9, 6, 5),
            ColourSet(6, 0, 13),
            ColourSet(5, 7, 2)))
        assertEquals(expected, actual)
    }

    @Test
    fun testParseSample() {
        assertEquals(ColourSet(9, 6, 5), gameBuilder.parseSample("9 red, 5 blue, 6 green"))
        assertEquals(ColourSet(6, 0, 13), gameBuilder.parseSample("6 red, 13 blue"))
        assertEquals(ColourSet(5, 7, 2), gameBuilder.parseSample("2 blue, 7 green, 5 red"))
    }

    @Test
    fun testIsSubset() {
        val bag = ColourSet(2, 2, 2)
        assertTrue(bag.isSubset(ColourSet(1, 1, 1)))
        assertTrue(bag.isSubset(ColourSet(2, 2, 2)))
        assertFalse(bag.isSubset(ColourSet(3, 2, 2)))
        assertFalse(bag.isSubset(ColourSet(2, 3, 2)))
        assertFalse(bag.isSubset(ColourSet(2, 2, 3)))
    }

    @Test
    fun testIsPossible() {
        val bag = ColourSet(12, 13, 14)

        val game1 = Game(1, listOf(
            ColourSet(4, 0, 3),
            ColourSet(1, 2, 6),
            ColourSet(0, 2, 0)
        ))
        assertTrue(game1.isPossibleFrom(bag))

        val game3 = Game(3, listOf(
            ColourSet(20, 8, 6),
            ColourSet(5, 13, 5),
            ColourSet(1, 5, 0)
        ))
        assertFalse(game3.isPossibleFrom(bag))
    }

    @Test
    fun testSmallestBag() {
        val game1 = Game(1, listOf(
            ColourSet(4, 0, 3),
            ColourSet(1, 2, 6),
            ColourSet(0, 2, 0)
        ))
        assertEquals(ColourSet(4, 2, 6), game1.smallestBag())

        val game3 = Game(3, listOf(
            ColourSet(20, 8, 6),
            ColourSet(5, 13, 5),
            ColourSet(1, 5, 0)
        ))
        assertEquals(ColourSet(20, 13, 6), game3.smallestBag())
    }

    @Test
    fun testPower() {
        assertEquals(48, ColourSet(4, 2, 6).power())
        assertEquals(1560, ColourSet(20, 13, 6).power())
    }
}
