package com.exubero.aoc.day03

fun main() {
    println("Advent of Code: Day 03")
    val inputData = Day03.loadInputData()
    println(inputData)
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