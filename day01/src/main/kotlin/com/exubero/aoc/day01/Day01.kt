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
}