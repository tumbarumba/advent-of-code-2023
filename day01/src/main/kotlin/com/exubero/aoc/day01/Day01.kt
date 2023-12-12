package com.exubero.aoc.day01

fun main() {
    println("Advent of Code: Day 01")
    val day01 = Day01()
    val total = day01.loadInputData().fold(0) {acc, next -> acc + day01.getValue(next)}
    println(total)
}

class Day01 {
    fun getFirstDigit(data: String): Char {
        return data.first { it.isDigit() }
    }

    fun getLastDigit(data: String): Char {
        return data.last { it.isDigit() }
    }

    fun getValue(data: String): Int {
        val valueString = this.getFirstDigit(data).toString() + this.getLastDigit(data)
        return valueString.toInt()
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        inputStream.use {
            return inputStream?.bufferedReader()?.readLines().orEmpty()
        }
    }
}