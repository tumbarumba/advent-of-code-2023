package com.exubero.aoc.day02

fun main() {
    println("Advent of Code: Day 02")
    val day02 = Day02()
}

class Day02 {
    companion object {
        val lineRegex = """^Game (\d+):.*$""".toRegex()
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        if (inputStream != null) {
            return inputStream.bufferedReader().readLines()
        }
        throw Exception("No input text found")
    }

    fun parseLine(line: String): Game {
        val match = lineRegex.find(line) ?: throw IllegalArgumentException("Not a game line: $line")
        val idStr = match.groups[1]!!.value
        return Game(idStr.toInt())
    }
}

data class Game(val id: Int)
