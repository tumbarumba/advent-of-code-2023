package com.exubero.aoc.day01

fun main() {
    println("Hello, world!")
}

class Day01 {
    fun getFirstDigit(data: String): Char {
        return data.first { it.isDigit() }
    }

    fun getLastDigit(data: String): Char {
        return data.last { it.isDigit() }
    }

    fun loadInputData(): List<String> {
        val inputStream = this::class.java.getResourceAsStream("/input.txt")
        inputStream.use {
            return inputStream?.bufferedReader()?.readLines().orEmpty()
        }
    }
}