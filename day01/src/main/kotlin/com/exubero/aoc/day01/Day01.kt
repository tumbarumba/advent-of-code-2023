package com.exubero.aoc.day01

fun main() {
    println("Advent of Code: Day 01")
    val day01 = Day01()
    val total = day01.loadInputData().fold(0) {acc, next -> acc + day01.getValue(next)}
    println("Calibration sum: $total")
}

class Day01 {
    companion object {
        val digitMap = hashMapOf(
            "one" to '1',
            "two" to '2',
            "three" to '3',
            "four" to '4',
            "five" to '5',
            "six" to '6',
            "seven" to '7',
            "eight" to '8',
            "nine" to '9'
        )
        val digitRegex = """(\d|one|two|three|four|five|six|seven|eight|nine)""".toRegex()
    }

    private fun charForDigit(digit: String): Char {
        val firstChar = digit.first()
        if (firstChar.isDigit()) {
            return firstChar
        }
        return digitMap.getOrElse(digit) { throw IllegalArgumentException("$digit is not a recognised digit") }
    }

    private fun maybeDigit(data: String): Char? {
        val match = digitRegex.find(data)
        if (match != null) {
            return charForDigit(match.value)
        }
        return null
    }

    fun getFirstDigit(data: String): Char {
        return maybeDigit(data) ?: throw IllegalArgumentException("No digit in $data")
    }

    fun getLastDigit(data: String): Char {
        for (i in data.length - 1 downTo 0) {
            val endOfData = data.substring(i, data.length)
            val digit = maybeDigit(endOfData)
            if (digit != null) {
                return digit
            }
        }
        throw IllegalArgumentException("No digit in $data")
    }

    fun getValue(data: String): Int {
        val valueString = this.getFirstDigit(data).toString() + this.getLastDigit(data)
        return valueString.toInt()
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        if (inputStream != null) {
            return inputStream.bufferedReader().readLines()
        }
        throw Exception("No input text found")
    }
}
