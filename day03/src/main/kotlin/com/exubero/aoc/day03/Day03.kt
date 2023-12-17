package com.exubero.aoc.day03

fun main() {
    println("Advent of Code: Day 03")
    val inputData = Day03.loadInputData()
    val parser = SchematicParser()
    val schematic = parser.makeSchematic(inputData)
    println(schematic)
}

class Day03 {
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

class SchematicParser {
    companion object {
        val numberRegex = """(\d+)""".toRegex()

        private fun makeSchematicNumber(group: MatchGroup, lineNumber: Int): SchematicNumber {
            val number = group.value.toInt()
            val location = Point(group.range.first + 1, lineNumber)
            val length = group.value.length
            return SchematicNumber(number, location, length)
        }
    }

    fun makeSchematic(lines: List<String>): Schematic {
        val parts = lines.flatMapIndexed() {index, line -> parseParts(index + 1, line)}
        val numbers = lines.flatMapIndexed() {index, line -> parseNumbers(index + 1, line)}
        return Schematic(parts, numbers)
    }

    fun parseParts(lineNumber: Int, line: String): List<SchematicPart> {
        return line.mapIndexed() {index, char -> Pair(index + 1, char)}
            .filter { !it.second.isDigit() && it.second != '.' }
            .map { SchematicPart(it.second, Point(it.first, lineNumber)) }
    }

    fun parseNumbers(lineNumber: Int, line: String): List<SchematicNumber> {
        return numberRegex.findAll(line)
            .map { it.groups[1]!! }
            .map { makeSchematicNumber(it, lineNumber) }
            .toList()
    }
}

data class Point(val x: Int, val y: Int)

data class SchematicNumber(val number: Int, val start: Point, val length: Int)

data class SchematicPart(val symbol: Char, val location: Point)

data class Schematic(val parts: List<SchematicPart>, val numbers: List<SchematicNumber>)