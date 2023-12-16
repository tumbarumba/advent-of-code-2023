package com.exubero.aoc.day02

fun main() {
    println("Advent of Code: Day 02")
    val day02 = Day02()
}

class Day02 {
    companion object {
        val gameRegex = """^Game (\d+): (.*)$""".toRegex()
        val colourRegex = """^\s*(\d+) (red|green|blue)$""".toRegex()
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        if (inputStream != null) {
            return inputStream.bufferedReader().readLines()
        }
        throw Exception("No input text found")
    }

    fun parseLine(line: String): Game {
        val match = gameRegex.find(line) ?: throw IllegalArgumentException("Not a game line: $line")
        val idStr = match.groups[1]!!.value
        val samples = match.groups[2]!!.value.split(";").map { parseSample(it) }
        return Game(idStr.toInt(), samples)
    }

    fun parseSample(text: String): GameSample {
        val colourMap = hashMapOf("red" to 0, "green" to 0, "blue" to 0)
        text.split(",").fold(colourMap) { acc, str ->
            val colourCount = parseColourCount(str)
            acc[colourCount.colour] = colourCount.count
            acc
        }
        return GameSample(colourMap["red"]!!, colourMap["green"]!!, colourMap["blue"]!!)
    }

    private fun parseColourCount(str: String): ColourCount {
        val match = colourRegex.find(str) ?: throw IllegalArgumentException("Not a colour: $str")
        val count = match.groups[1]!!.value.toInt()
        val colour = match.groups[2]!!.value
        return ColourCount(colour, count)
    }
}

data class ColourCount(val colour: String, val count: Int)

data class GameSample(val red: Int, val green: Int, val blue: Int)

data class Game(val id: Int, val samples: List<GameSample>)
