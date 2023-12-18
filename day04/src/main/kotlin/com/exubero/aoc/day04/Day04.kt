package com.exubero.aoc.day04

fun main() {
    println("Advent of Code: Day 04")
    Day04.loadInputData()
}

class Day04 {
    companion object {
        fun loadInputData(): List<String> {
            val inputStream = this::class.java.getResourceAsStream("/input.txt")
            if (inputStream != null) {
                return inputStream.bufferedReader().readLines()
            }
            throw Exception("No input text found")
        }
    }
}

class ScratchCardParser {
    companion object {
        val scratchCardRegex = """^Card (\d+):(.*)\|(.*)$""".toRegex()
    }

    private fun parseNumbers(numbersText: String): Set<Int> {
        return numbersText.split("""\s+""".toRegex())
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .toSet()
    }

    fun parseLine(line: String): ScratchCard {
        val match = scratchCardRegex.find(line) ?: throw IllegalArgumentException("Not a scratchcard: $line")
        val id = match.groups[1]!!.value.toInt()
        val winning = parseNumbers(match.groups[2]!!.value)
        val selected = parseNumbers(match.groups[3]!!.value)
        return ScratchCard(id, winning, selected)
    }
}

data class ScratchCard(val id: Int, val winningNumbers: Set<Int>, val selectedNumbers: Set<Int>)
