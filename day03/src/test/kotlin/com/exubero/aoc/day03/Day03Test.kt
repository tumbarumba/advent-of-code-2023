package com.exubero.aoc.day03

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day03Test {
    private val parser = SchematicParser()

    @Test
    fun testParseParts() {
        val parts1 = parser.parseParts(1, "467..114..")
        val expected1 = listOf<SchematicPart>()
        assertEquals(expected1, parts1)

        val parts2 = parser.parseParts(9, "...\$.*....")
        val expected2 = listOf(
            SchematicPart('$', Point(4, 9)),
            SchematicPart('*', Point(6, 9))
        )
        assertEquals(expected2, parts2)
    }

    @Test
    fun testParseNumbers() {
        val numbers1 = parser.parseNumbers(1, "467..114..")
        val expected1 = listOf(
            SchematicNumber(467, Point(1, 1), 3),
            SchematicNumber(114, Point(6, 1), 3)
        )
        assertEquals(expected1, numbers1)

        val numbers2 = parser.parseNumbers(9, "...\$.*....")
        val expected2 = listOf<SchematicNumber>()
        assertEquals(expected2, numbers2)
    }
}