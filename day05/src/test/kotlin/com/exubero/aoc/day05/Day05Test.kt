package com.exubero.aoc.day05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day05Test {
    private val parser = Day05DataParser()

    companion object {
        val seedToSoilData = listOf("50 98 2", "52 50 48")
        val seedToSoilLookup = Day05DataParser().parseLookupData(seedToSoilData)

        @JvmStatic
        fun expectedSeedToSoil(): Stream<Arguments> = Stream.of(
            Arguments.of(LongRange(0, 1), listOf(LongRange(0, 1))),
            Arguments.of(LongRange(49, 51), listOf(LongRange(49, 49), LongRange(52, 53))),
            Arguments.of(LongRange(97, 100), listOf(LongRange(97, 97), LongRange(100, 100), LongRange(50, 51)))
        )
    }

    @ParameterizedTest
    @MethodSource("expectedSeedToSoil")
    fun testMapLookup(seedRange: LongRange, expectedSoilRanges: List<LongRange>) {
        assertEquals(expectedSoilRanges, seedToSoilLookup.destinationsFor(seedRange))
    }

    @Test
    fun testParseSeeds() {
        val expectedSeedRanges = listOf(LongRange(79L, 92L), LongRange(55L, 67L))
        assertEquals(expectedSeedRanges, parser.parseSeeds("79 14 55 13"))
    }

    @Test
    fun testMapRangeIntersectsSource() {
        val mapRange = MapRange(500, 100, 10)
        val fullyInsideRange = LongRange(101, 105)
        val partialLeftRange = LongRange(99, 105)
        val partialRightRange = LongRange(105, 115)
        val surroundingRange = LongRange(99, 115)
        val outsideLeftRange = LongRange(20, 22)
        val outsideRightRange = LongRange(150, 155)

        assertTrue(mapRange.intersectsSource(fullyInsideRange))
        assertTrue(mapRange.intersectsSource(partialLeftRange))
        assertTrue(mapRange.intersectsSource(partialRightRange))
        assertTrue(mapRange.intersectsSource(surroundingRange))
        assertFalse(mapRange.intersectsSource(outsideLeftRange))
        assertFalse(mapRange.intersectsSource(outsideRightRange))
    }

    @Test
    fun testMapRangeConvert() {
        val mapRange = MapRange(500, 100, 10)

        val fullyInsideRange = 101L..105L
        assertEquals(listOf(501L..505L), mapRange.convert(fullyInsideRange))

        val partialLeftRange = 99L..105L
        assertEquals(listOf(99L..99L, 500L..505L), mapRange.convert(partialLeftRange))

        val partialRightRange = 105L..115L
        assertEquals(listOf(505L..509L, 110L..115L), mapRange.convert(partialRightRange))

        val surroundingRange = 99L..115L
        assertEquals(listOf(99L..99L, 110L..115L, 500L..509L), mapRange.convert(surroundingRange))

        val outsideLeftRange = 20L..22L
        assertEquals(listOf(20L..22L), mapRange.convert(outsideLeftRange))

        val outsideRightRange = 150L..155L
        assertEquals(listOf(150L..155L), mapRange.convert(outsideRightRange))
    }
}