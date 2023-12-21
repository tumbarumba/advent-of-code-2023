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
        .flatMap { seedToSoilLookup.destinationsFor(it) }
        .flatMap { soilToFertilizerLookup.destinationsFor(it) }
        .flatMap { fertilizerToWaterLookup.destinationsFor(it) }
        .flatMap { waterToLightLookup.destinationsFor(it) }
        .flatMap { lightToTemperatureLookup.destinationsFor(it) }
        .flatMap { temperatureToHumidityLookup.destinationsFor(it) }
        .flatMap { humidityToLocationLookup.destinationsFor(it) }
        .flatten()
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
        return seedData.split(" ")
            .chunked(2)
            .map { makeSeedRange(it) }
    }

    private fun makeSeedRange(seedPair: List<String>): LongRange {
        val start = seedPair.first().toLong()
        val length = seedPair.last().toLong()
        return LongRange(start, start + length - 1)
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

class MapRange(destinationStart: Long, sourceStart: Long, rangeLength: Long) {
    private val thisSourceRange = LongRange(sourceStart, sourceStart + rangeLength - 1)
    private val thisDestinationRange = LongRange(destinationStart, destinationStart + rangeLength - 1)

    fun intersectsSource(otherSourceRange: LongRange): Boolean {
        return thisSourceRange.contains(otherSourceRange.first) ||
                thisSourceRange.contains(otherSourceRange.last) ||
                (otherSourceRange.first < thisSourceRange.first && otherSourceRange.last > thisSourceRange.last)
    }

    fun convert(otherSourceRange: LongRange): List<LongRange> {
        if (thisSourceRange.contains(otherSourceRange.first) && thisSourceRange.contains(otherSourceRange.last)) {
            // Other source is fully contained in this source range
            return listOf(toDestination(otherSourceRange))
        }
        if (otherSourceRange.first < thisSourceRange.first) {
            if (otherSourceRange.last > thisSourceRange.last) {
                // Other source fully overlaps mapping source, with extra on both sides
                val unmappedLeft = LongRange(otherSourceRange.first, thisSourceRange.first - 1)
                val unmappedRight = LongRange(thisSourceRange.last + 1, otherSourceRange.last)
                return listOf(unmappedLeft, unmappedRight, thisDestinationRange)
            }
            if (otherSourceRange.last >= thisSourceRange.first) {
                // Other source partially overlaps mapping source on the left
                val unmappedLeft = LongRange(otherSourceRange.first, thisSourceRange.first - 1)
                val mappedSize = otherSourceRange.last - thisSourceRange.first + 1
                val mapped = LongRange(thisDestinationRange.first, thisDestinationRange.first + mappedSize - 1)
                return listOf(unmappedLeft, mapped)
            }
            // Other source is left of mapping source, and doesn't overlap
            listOf(otherSourceRange)
        }
        if (otherSourceRange.last > thisSourceRange.last && otherSourceRange.first <= thisSourceRange.last) {
            // Other source partially overlaps mapping source on the right
            val unmappedRight = LongRange(thisSourceRange.last + 1, otherSourceRange.last)
            val otherSize = otherSourceRange.last - otherSourceRange.first + 1
            val unmappedSize = unmappedRight.last - unmappedRight.first + 1
            val mappedSize = otherSize - unmappedSize
            val mapped = LongRange(thisDestinationRange.last - mappedSize + 1, thisDestinationRange.last)
            return listOf(mapped, unmappedRight)
        }
        // Other source is right of mapping source, and doesn't overlap
        return listOf(otherSourceRange)
    }

    private fun toDestination(otherSourceRange: LongRange): LongRange {
        val startOffset = otherSourceRange.first - thisSourceRange.first
        val start = thisDestinationRange.first() + startOffset
        val end = start + otherSourceRange.last - otherSourceRange.first
        return LongRange(start, end)
    }
}

class MapLookup(val mapRanges: List<MapRange>) {
    fun destinationsFor(sourceRange: LongRange): List<LongRange> {
        val sources = mutableListOf(sourceRange)
        val result = mutableListOf<LongRange>()
        mapRanges.filter { it.intersectsSource(sourceRange) }.forEach {
            if (it.intersectsSource(sourceRange)) {
                result.addAll(it.convert(sourceRange))
            }
        }
//        if (intersectingRanges.isNotEmpty()) {
//            return intersectingRanges.flatMap { it.convert(sourceRange) }
//        }
        return result
    }
}
