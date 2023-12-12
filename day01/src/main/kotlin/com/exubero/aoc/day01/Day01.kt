package com.exubero.aoc.day01

fun main() {
    println("Hello, world!")
}

class Day01 {
    fun getFirstDigit(data: String): Char {
        return data.filter { it.isDigit() }.first()
    }
}