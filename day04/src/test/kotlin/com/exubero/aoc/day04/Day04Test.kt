package com.exubero.aoc.day04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04Test {
    private val parser = ScratchCardParser()

    @Test
    fun testParseLine() {
        val card = parser.parseLine("Card   1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        assertEquals(ScratchCard(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)), card)
    }

    @Test
    fun testSelectedWinningNumbers() {
        val card = ScratchCard(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53))
        assertEquals(setOf(48, 83, 17, 86), card.selectedWinning())
    }

    @Test
    fun testPoints() {
        assertEquals(8, parser.parseLine("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53").points())
        assertEquals(2, parser.parseLine("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19").points())
        assertEquals(2, parser.parseLine("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1").points())
        assertEquals(1, parser.parseLine("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83").points())
        assertEquals(0, parser.parseLine("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36").points())
        assertEquals(0, parser.parseLine("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11").points())
    }
}