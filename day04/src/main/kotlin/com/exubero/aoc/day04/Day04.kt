package com.exubero.aoc.day04

fun main() {
    println("Advent of Code: Day 04")
    Day04.loadInputData()
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
