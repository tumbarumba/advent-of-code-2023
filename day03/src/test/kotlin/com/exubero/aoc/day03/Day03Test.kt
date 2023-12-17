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

    @Test
    fun testNumberAdjacentBox() {
        assertEquals(
            Box(Point(0, 0), Point(2, 2)),
            SchematicNumber(1, Point(1, 1), 1).adjacentBox())
        assertEquals(
            Box(Point(9, 9), Point(13, 11)),
            SchematicNumber(200, Point(10, 10), 3).adjacentBox())
    }

    @Test
    fun testBoxContainsPoint() {
        val box = Box(Point(10, 10), Point(14, 12))
        assertTrue(box.contains(Point(10, 10)))
        assertTrue(box.contains(Point(14, 12)))
        assertFalse(box.contains(Point(9, 10)))
        assertFalse(box.contains(Point(10, 9)))
        assertFalse(box.contains(Point(15, 15)))
    }

    private val schematic = parser.makeSchematic(
        listOf(
            """467..114..""",
            """...*......""",
            """..35..633.""",
            """......#...""",
            """617*......""",
            """.....+.58.""",
            """..592.....""",
            """......755.""",
            """...$.*....""",
            """.664.598.."""
        )
    )

    @Test
    fun testSchematicIsPartNumber() {
        assertTrue(schematic.isPartNumber(SchematicNumber(467, Point(1, 1), 3)))
        assertTrue(schematic.isPartNumber(SchematicNumber(598, Point(6, 10), 3)))
        assertFalse(schematic.isPartNumber(SchematicNumber(114, Point(6, 1), 3)))
        assertFalse(schematic.isPartNumber(SchematicNumber(58, Point(8, 6), 2)))
    }

    @Test
    fun testSchematicPartNumbers() {
        assertEquals(listOf(467, 35, 633, 617, 592, 755, 664, 598), schematic.partNumbers())
    }

    @Test
    fun testNumbersForPart() {
        assertEquals(listOf(467, 35), schematic.partNumbersFor(SchematicPart('*', Point(4, 2))))
    }

    @Test
    fun testGears() {
        val expected = listOf(
            Gear(Point(4, 2), 16345),
            Gear(Point(6, 9), 451490)
        )
        assertEquals(expected, schematic.gears())
    }
}