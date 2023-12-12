package com.exubero.aoc.day01

fun main() {
    println("Advent of Code: Day 01")
    val day01 = Day01()
    val total = day01.loadInputData().fold(0) {acc, next -> acc + day01.getValue(next)}
    println(total)
    // 52638 is too low?
}

class Day01 {
    fun normalise(data: String): String {
        return data
            .replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
    }

    fun getFirstDigit(data: String): Char {
        return data.first { it.isDigit() }
    }

    fun getLastDigit(data: String): Char {
        return data.last { it.isDigit() }
    }

    fun getValue(data: String): Int {
        val normalData = this.normalise(data)
        val valueString = this.getFirstDigit(normalData).toString() + this.getLastDigit(normalData)
        return valueString.toInt()
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        inputStream.use {
            return inputStream.bufferedReader().readLines().orEmpty()
        }
    }
}