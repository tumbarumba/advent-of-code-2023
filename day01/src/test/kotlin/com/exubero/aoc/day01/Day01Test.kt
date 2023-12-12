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
                Arguments.of("two934seven1", "293471", '2', '1', 21),
                Arguments.of("8825eightknfv", "88258knfv", '8', '8', 88),
                Arguments.of("4twofour", "424", '4', '4', 44)
            )
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testNormaliseData(data: String, normalData: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(normalData, day01.normalise(data))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testExtractFirstDigit(data: String, normalData: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(firstDigit, day01.getFirstDigit(normalData))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testExtractLastDigit(data: String, normalData: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(lastDigit, day01.getLastDigit(normalData))
    }

    @ParameterizedTest
    @MethodSource("extractArguments")
    @Suppress("UNUSED_PARAMETER")
    fun testExtractValue(data: String, normalData: String, firstDigit: Char, lastDigit: Char, value: Int) {
        assertEquals(value, day01.getValue(data))
    }

    @Test
    fun loadInputData() {
        val inputData = day01.loadInputData()
        assertEquals("two934seven1", inputData.first())
        assertEquals("fivebxsevensixone872dlx", inputData.last())
    }
}
