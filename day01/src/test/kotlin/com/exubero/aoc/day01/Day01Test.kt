package com.exubero.aoc.day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day01Test {
    private val day01 = Day01()

    companion object ExtractParameters {
        @JvmStatic
        fun extractArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of("two934seven1", '9', '1', 91),
                Arguments.of("8825eightknfv", '8', '5', 85),
                Arguments.of("4twofour", '4', '4', 44)
            )
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    fun testExtractFirstDigit(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(firstDigit, day01.getFirstDigit(data))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    fun testExtractLastDigit(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(lastDigit, day01.getLastDigit(data))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    fun testExtractValue(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(value, day01.getValue(data))
    }

    @Test
    fun loadInputData() {
        val inputData = day01.loadInputData()
        assertEquals("two934seven1", inputData.first())
        assertEquals("fivebxsevensixone872dlx", inputData.last())
    }
}
