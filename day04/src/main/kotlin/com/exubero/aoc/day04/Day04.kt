package com.exubero.aoc.day04

import kotlin.math.pow

fun main() {
    println("Advent of Code: Day 04")
    val parser = ScratchCardParser()
    val cards = Day04.loadInputData().map { parser.parseLine(it) }
    val sumOfPoints = cards.sumOf { it.points() }
    println("Sum of scratchcard points: $sumOfPoints")
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
        val scratchCardRegex = """^Card\s+(\d+):(.*)\|(.*)$""".toRegex()
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

data class ScratchCard(val id: Int, val winningNumbers: Set<Int>, val selectedNumbers: Set<Int>) {
    fun selectedWinning(): Set<Int> {
        return winningNumbers intersect selectedNumbers
    }

    fun points(): Int {
        val winningCount = selectedWinning().size
        if (winningCount == 0) {
            return 0
        }
        return 2.0.pow(winningCount - 1).toInt()
    }
}
