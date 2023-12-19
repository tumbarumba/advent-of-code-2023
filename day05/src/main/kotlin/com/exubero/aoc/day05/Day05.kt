package com.exubero.aoc.day05

fun main() {
    println("Advent of Code: Day 05")

    val parser = Day05DataParser()
    val seeds = parser.parseSeeds(Day05.loadSeedsData())
    val seedToSoilLookup = parser.parseLookupData(Day05.loadSeedToSoilData())
    val soilToFertilizerLookup = parser.parseLookupData(Day05.loadSoilToFertilizerData())
    val fertilizerToWaterLookup = parser.parseLookupData(Day05.loadFertilizerToWaterData())
    val waterToLightLookup = parser.parseLookupData(Day05.loadWaterToLightData())
    val lightToTemperatureLookup = parser.parseLookupData(Day05.loadLightToTemperatureData())
    val temperatureToHumidityLookup = parser.parseLookupData(Day05.loadTemperatureToHumidityData())
    val humidityToLocationLookup = parser.parseLookupData(Day05.loadHumidityToLocationData())

    val smallestLocation = seeds.asSequence()
        .flatten()
        .map { seedToSoilLookup.destinationFor(it) }
        .map { soilToFertilizerLookup.destinationFor(it) }
        .map { fertilizerToWaterLookup.destinationFor(it) }
        .map { waterToLightLookup.destinationFor(it) }
        .map { lightToTemperatureLookup.destinationFor(it) }
        .map { temperatureToHumidityLookup.destinationFor(it) }
        .map { humidityToLocationLookup.destinationFor(it) }
        .min()
    println("Smallest location: $smallestLocation")
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

        fun loadSeedsData()                 = loadInputData("/seeds.txt").first()
        fun loadSeedToSoilData()            = loadInputData("/seed-to-soil.txt")
        fun loadSoilToFertilizerData()      = loadInputData("/soil-to-fertilizer.txt")
        fun loadFertilizerToWaterData()     = loadInputData("/fertilizer-to-water.txt")
        fun loadWaterToLightData()          = loadInputData("/water-to-light.txt")
        fun loadLightToTemperatureData()    = loadInputData("/light-to-temperature.txt")
        fun loadTemperatureToHumidityData() = loadInputData("/temperature-to-humidity.txt")
        fun loadHumidityToLocationData()    = loadInputData("/humidity-to-location.txt")
    }
}

class Day05DataParser {
    companion object {
        val mapDataRegex = """^(\d+) (\d+) (\d+)$""".toRegex()
    }

    fun parseSeeds(seedData: String): List<LongRange> {
        val values = seedData.split(" ").map { it.toLong() }
        val ranges = mutableListOf<LongRange>()
        for (i in values.indices step 2) {
            val start = values[i]
            val end = start + values[i+1] - 1
            ranges.add(LongRange(start, end))
        }
        return ranges
    }

    fun parseMapDataLine(line: String): MapRange {
        val match = mapDataRegex.find(line) ?: throw IllegalArgumentException("Not valid map data: $line")
        val destination = match.groups[1]!!.value.toLong()
        val source      = match.groups[2]!!.value.toLong()
        val length      = match.groups[3]!!.value.toLong()
        return MapRange(destination, source, length)
    }

    fun parseLookupData(seedToSoilData: List<String>): MapLookup {
        val mapRanges = seedToSoilData.map { parseMapDataLine(it) }
        return MapLookup(mapRanges)
    }

}

class MapRange(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    fun contains(source: Long): Boolean {
        return (source >= sourceStart) && (source < sourceStart + rangeLength)
    }

    fun convert(source: Long): Long {
        val offset = source - sourceStart
        return destinationStart + offset
    }
}

class MapLookup(val mapRanges: List<MapRange>) {
    fun destinationFor(source: Long): Long {
        return mapRanges.find { it.contains(source) }?.convert(source) ?: source
    }
}
