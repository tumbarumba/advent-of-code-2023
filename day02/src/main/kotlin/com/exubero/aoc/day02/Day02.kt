package com.exubero.aoc.day02

fun main() {
    println("Advent of Code: Day 02")
    val day02 = Day02()
}

class Day02 {
    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        if (inputStream != null) {
            return inputStream.bufferedReader().readLines()
        }
        throw Exception("No input text found")
    }
}
