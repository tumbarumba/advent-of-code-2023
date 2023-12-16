package com.exubero.aoc.day02

fun main() {
    println("Advent of Code: Day 02")
    val day02 = Day02()
    day02.loadInputData()
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

    fun parseSample(text: String): ColourSet {
        val colourMap = hashMapOf("red" to 0, "green" to 0, "blue" to 0)
        text.split(",")
            .map { parseColourCount(it) }
            .fold(colourMap) { acc, cc -> acc[cc.colour] = cc.count; acc }
        return ColourSet(colourMap["red"]!!, colourMap["green"]!!, colourMap["blue"]!!)
    }

    private fun parseColourCount(str: String): ColourCount {
        val match = colourRegex.find(str) ?: throw IllegalArgumentException("Not a colour: $str")
        val count = match.groups[1]!!.value.toInt()
        val colour = match.groups[2]!!.value
        return ColourCount(colour, count)
    }
}

data class ColourCount(val colour: String, val count: Int)

data class ColourSet(val red: Int, val green: Int, val blue: Int) {
    fun isSubset(sample: ColourSet): Boolean {
        return sample.red <= red && sample.green <= green && sample.blue <= blue
    }
}

data class Game(val id: Int, val samples: List<ColourSet>)
