package com.exubero.aoc.day03

fun main() {
    println("Advent of Code: Day 03")
    val inputData = Day03.loadInputData()
    val parser = SchematicParser()
    val schematic = parser.makeSchematic(inputData)

    val sumOfPartNumbers = schematic.partNumbers().sum()
    println("Sum of part numbers: $sumOfPartNumbers")

    val sumOfGearRatios = schematic.gears().sumOf { it.ratio }
    println("Sum of gear ratios: $sumOfGearRatios")
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

data class Box(val topLeft: Point, val bottomRight: Point) {
    fun contains(point: Point): Boolean {
        return point.x >= topLeft.x &&
                point.y >= topLeft.y &&
                point.x <= bottomRight.x &&
                point.y <= bottomRight.y
    }
}

data class SchematicNumber(val number: Int, val start: Point, val length: Int) {
    fun adjacentBox(): Box {
        return Box(
            Point(start.x - 1, start.y - 1),
            Point(start.x + length, start.y + 1)
        )
    }
}

data class SchematicPart(val symbol: Char, val location: Point)

data class Gear(val location: Point, val ratio: Int)

data class Schematic(val parts: List<SchematicPart>, val numbers: List<SchematicNumber>) {
    fun isPartNumber(number: SchematicNumber): Boolean {
        return parts.any() { number.adjacentBox().contains(it.location) }
    }

    fun partNumbers(): List<Int> {
        return numbers
            .filter { isPartNumber(it) }
            .map { it.number }
    }

    fun partNumbersFor(part: SchematicPart): List<Int> {
        return numbers
            .filter { it.adjacentBox().contains(part.location) }
            .map { it.number }
    }

    fun gears(): List<Gear> {
        return parts
            .filter { isGear(it) }
            .map { makeGear(it) }
    }

    private fun isGear(part: SchematicPart): Boolean {
        if (part.symbol != '*') {
            return false
        }
        val partNumbers = partNumbersFor(part)
        return partNumbers.size == 2
    }

    private fun makeGear(part: SchematicPart): Gear {
        val numbers = partNumbersFor(part)
        val ratio = numbers[0] * numbers[1]
        return Gear(part.location, ratio)
    }
}