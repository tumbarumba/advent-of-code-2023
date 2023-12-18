package com.exubero.aoc.day04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04Test {
    private val parser = ScratchCardParser()

    @Test
    fun testParseLine() {
        val card = parser.parseLine("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        assertEquals(ScratchCard(1, setOf(41, 48, 83, 86, 17), setOf(83, 86, 6, 31, 17, 9, 48, 53)), card)
    }
}