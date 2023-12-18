class Day05(private val input: List<String> = emptyList()) {

    private val sections = input.joinToString().split(", , ")
    private val seedsPart1 = sections[0].split(": ")[1].split(" ").map { it.toLong() }
    private val seedsPart2 = expandSeeds(seedsPart1)
    private val seedToSoilMap = parseMap(sections[1])
    private val soilToFertilizerMap = parseMap(sections[2])
    private val fertilizerToWaterMap = parseMap(sections[3])
    private val waterToLightMap = parseMap(sections[4])
    private val lightToTemperatureMap = parseMap(sections[5])
    private val temperatureToHumidityMap = parseMap(sections[6])
    private val humidityToLocationMap = parseMap(sections[7])

    data class Mapping(val dest: Long, val src: Long, val mapLength: Long)

    fun expandSeeds(xs: List<Long>): List<LongRange> {
        val pairs = xs.withIndex().groupBy { it.index / 2 }
        val pairValues = pairs.map { it.value.map { it.value } }
        return pairValues.map { it.first().rangeUntil(it.first() + it.last()) }
    }

    private fun findMapping(source: Long, mapping: List<Mapping>): Long {
        if (mapping.isEmpty()) return source
        val head = mapping.first()
        return if (source in head.src..<head.src + head.mapLength) {
            source + (head.dest - head.src)
        } else {
            findMapping(source, mapping.drop(1))
        }
    }

    private fun parseMap(s: String): List<Mapping> {
        fun strToMapping(str: String): Mapping {
            val numbers = str.split(" ").map { it.toLong() }
            return Mapping(numbers[0], numbers[1], numbers[2])
        }
        return s.split(", ").drop(1).map { strToMapping(it) }
    }

    private fun getAnswer(seeds: List<Long>): Long {
        val locations = seeds.map { findMapping(it, seedToSoilMap) }.map { findMapping(it, soilToFertilizerMap) }
            .map { findMapping(it, fertilizerToWaterMap) }
            .map { findMapping(it, waterToLightMap) }.map { findMapping(it, lightToTemperatureMap) }
            .map { findMapping(it, temperatureToHumidityMap) }
            .map { findMapping(it, humidityToLocationMap) }
        return locations.min()
    }

    /***
     * Super inefficient solution. Took an hour to run and I tried submitting an intermediary result after only 30 %
     * of the seeds were processed.
     */
    private fun getAnswerRanges(seeds: List<LongRange>): Long {
        var result = seeds.first().last // initialize with first maximum, then walk it down
        for ((index, seed) in seeds.withIndex()) {
            println("Starting index $index/${seeds.size - 1}, current result: $result")
            for (i in seed) {
                val soil = findMapping(i, seedToSoilMap)
                val fertilizer = findMapping(soil, soilToFertilizerMap)
                val water = findMapping(fertilizer, fertilizerToWaterMap)
                val light = findMapping(water, waterToLightMap)
                val temperature = findMapping(light, lightToTemperatureMap)
                val humidity = findMapping(temperature, temperatureToHumidityMap)
                val location = findMapping(humidity, humidityToLocationMap)
                if (location < result) result = location
            }
        }
        return result
    }

    fun part1(): Long {
        return getAnswer(seedsPart1)
    }

    fun part2(): Long {
        return getAnswerRanges(seedsPart2)
    }
}

fun main() {
    val input = readInput("Day05")
    Day05(input).part1().println()
    Day05(input).part2().println()
}