package com.exubero.aoc.day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CalibrationValueExtractorTest {
    private val extractor = CalibrationValueExtractor()

    companion object ExtractParameters {
        @JvmStatic
        fun extractArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of("two1nine", '2', '9', 29),
                Arguments.of("eightwothree", '8', '3', 83),
                Arguments.of("abcone2threexyz", '1', '3', 13),
                Arguments.of("xtwone3four", '2', '4', 24),
                Arguments.of("4nineeightseven2", '4', '2', 42),
                Arguments.of("zoneight234", '1', '4', 14),
                Arguments.of("7pqrstsixteen", '7', '6', 76),
                Arguments.of("6bjztkxhsixkgnkroneightht", '6', '8', 68),
                Arguments.of("oneight", '1', '8', 18),
                Arguments.of("twone", '2', '1', 21),
                Arguments.of("threeight", '3', '8', 38),
                Arguments.of("fiveight", '5', '8', 58),
                Arguments.of("sevenine", '7', '9', 79),
                Arguments.of("eighthree", '8', '3', 83),
                Arguments.of("nineight", '9', '8', 98)
            )
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testFirstDigit(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(firstDigit, extractor.firstDigit(data))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testLastDigit(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(lastDigit, extractor.lastDigit(data))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testCalibrationValue(data: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(value, extractor.calibrationValueFor(data))
    }

    @Test
    fun testLoadInputData() {
        val inputData = extractor.loadInputData()
        assertEquals("two934seven1", inputData.first())
        assertEquals("fivebxsevensixone872dlx", inputData.last())
    }
}
