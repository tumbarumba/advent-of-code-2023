package com.exubero.aoc.day05

fun main() {
    println("Advent of Code: Day 05")
}

class Day05 {
    companion object {
        private fun loadInputData(path: String): List<String> {
            val inputStream = this::class.java.getResourceAsStream(path)
            if (inputStream != null) {
                return inputStream.bufferedReader().readLines()
            }
            throw Exception("No input text found")
        }

        fun loadSeedsData()                 = loadInputData("/seeds.txt")
        fun loadSeedToSoilData()            = loadInputData("/seed-to-soil.txt")
        fun loadSoilToFertilizerData()      = loadInputData("/soil-to-fertilizer.txt")
        fun loadFertilizerToWaterData()     = loadInputData("/fertilizer-to-water.txt")
        fun loadWaterToLightData()          = loadInputData("/water-to-light.txt")
        fun loadLightToTemperatureData()    = loadInputData("/light-to-temperature.txt")
        fun loadTemperatureToHumidityData() = loadInputData("/temperature-to-humidity.txt")
        fun loadHumidityToLocationData()    = loadInputData("/humidity-to-location.txt")
    }
}

typealias SeedNumber = Int
typealias SoilType = Int

class Day05DataParser {
    companion object {
        val mapDataRegex = """^(\d+) (\d+) (\d+)$""".toRegex()
    }

    fun parseSeeds(seedData: String): List<SeedNumber> {
        return seedData.split(" ").map { it.toInt() }
    }

    fun parseMapDataLine(line: String): MapRange {
        val match = mapDataRegex.find(line) ?: throw IllegalArgumentException("Not valid map data: $line")
        val destination = match.groups[1]!!.value.toInt()
        val source      = match.groups[2]!!.value.toInt()
        val length      = match.groups[3]!!.value.toInt()
        return MapRange(destination, source, length)
    }

    fun parseSeedToSoilData(seedToSoilData: List<String>): SeedToSoilLookup {
        val mapRanges = seedToSoilData.map { parseMapDataLine(it) }
        return SeedToSoilLookup(mapRanges)
    }

}

class MapRange(val destinationStart: Int, val sourceStart: Int, val rangeLength: Int) {
    fun contains(source: Int): Boolean {
        return (source >= sourceStart) && (source < sourceStart + rangeLength)
    }

    fun lookup(source: Int): Int {
        val offset = source - sourceStart
        return destinationStart + offset
    }
}

class SeedToSoilLookup(val mapRanges: List<MapRange>) {
    fun soilFor(source: SeedNumber): SoilType {
        return mapRanges.find { it.contains(source) }?.lookup(source) ?: source
    }
}
