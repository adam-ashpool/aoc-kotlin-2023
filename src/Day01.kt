fun main() {
    val digitMapping = mapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4",
        "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")

    fun getCalibrationValue(s: String, regex: Regex): Int {
        val matches = regex.findAll(s).map { it.groupValues[1] }
        val matchesAsDigits = matches.map { digitMapping.getOrDefault(it, it) }
        val result = "${matchesAsDigits.first()}${matchesAsDigits.last()}"
        return result.toInt()
    }

    fun part1(input: List<String>): Int {
        val regex = Regex("(\\d)") // only digits count
        return input.sumOf { getCalibrationValue(it, regex) }
    }

    fun part2(input: List<String>): Int {
        // now string representations also count
        // needs the lookahead with ?= - example: "eighthree" should return 83, not 88
        val regex = Regex("(?=(one|two|three|four|five|six|seven|eight|nine|\\d))")
        return input.sumOf { getCalibrationValue(it, regex) }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01_test")) == 142)
    check(part2(readInput("Day01_test2")) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
