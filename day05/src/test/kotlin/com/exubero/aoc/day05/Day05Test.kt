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

        @JvmStatic
        fun expectedSeedToSoil(): Stream<Arguments> = Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 1),
            Arguments.of(49, 49),
            Arguments.of(50, 52),
            Arguments.of(51, 53),
            Arguments.of(97, 99),
            Arguments.of(98, 50),
            Arguments.of(99, 51)
        )
    }

    @Test
    fun testParseSeeds() {
        val expectedSeedRanges = listOf(LongRange(79L, 92L), LongRange(55L, 67L))
        assertEquals(expectedSeedRanges, parser.parseSeeds("79 14 55 13"))
    }

    @ParameterizedTest
    @MethodSource("expectedSeedToSoil")
    fun testParseSeedToSoil(seed: Long, expectedSoil: Long) {
        val lookup = parser.parseLookupData(seedToSoilData)
        assertEquals(expectedSoil, lookup.destinationFor(seed))
    }
}