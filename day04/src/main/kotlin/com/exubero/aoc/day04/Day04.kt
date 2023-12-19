package com.exubero.aoc.day04

import kotlin.math.pow

fun main() {
    println("Advent of Code: Day 04")
    val parser = ScratchCardParser()
    val cards = Day04.loadInputData().map { parser.parseLine(it) }

    val sumOfPoints = cards.sumOf { it.points() }
    println("Sum of scratchcard points: $sumOfPoints")

    val processor = ScratchCardProcessor()
    val processedCards = processor.processCards(cards)
    println("Final number of scratchcards: ${processedCards.size}")
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

data class ScratchCard(val number: Int, val winningNumbers: Set<Int>, val selectedNumbers: Set<Int>) {
    fun selectedWinning() = winningNumbers intersect selectedNumbers

    fun winningCount(): Int {
        return selectedWinning().size
    }

    fun points(): Int {
        if (winningCount() == 0) {
            return 0
        }
        return 2.0.pow(winningCount() - 1).toInt()
    }
}

class ScratchCardProcessor {
    fun processCards(originalCards: List<ScratchCard>): List<ScratchCard> {
        val cardMap = originalCards.map { it.number to mutableListOf(it) }.toMap()
        for (cardNumber in  cardMap.keys.sorted()) {
            for (card in cardMap[cardNumber]!!) {
                for (i in 1..card.winningCount()) {
                    val copies = cardMap[cardNumber + i]
                    if (copies != null) {
                        val cardToCopy = copies.first()
                        copies.add(cardToCopy.copy())
                    }
                }
            }
        }
        return cardMap.keys.sorted().flatMap { cardMap[it]!! }
    }

}
