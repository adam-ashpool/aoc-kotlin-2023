class Day06(private val input: List<String> = emptyList()) {

    private fun isWinningRace(timeHeld: Long, race: Race): Boolean {
        return timeHeld * (race.time - timeHeld) > race.distance
    }

    /***
     * Calculates number of possibilities of held times that win the race.
     */
    private fun calculateMargin(race: Race): Int {
        val margin = (0..race.time).filter { isWinningRace(it, race) }
        return margin.count()
    }

    private fun parseInput(): List<Race> {
        val times = input[0].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        val distances = input[1].split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        return times.zip(distances).map { Race(it.first, it.second) }
    }

    private fun parseLongRace(): Race {
        val time = input[0].split(":")[1].trim().replace("\\s+".toRegex(), "").toLong()
        val distance = input[1].split(":")[1].trim().replace("\\s+".toRegex(), "").toLong()
        return Race(time, distance)
    }

    private val races = parseInput()
    private val longRace = parseLongRace()
    data class Race(val time: Long, val distance: Long)

    fun part1(): Int {
        val margins = races.map { calculateMargin(it) }
        return margins.fold(1) { acc, i -> acc * i }
    }

    fun part2(): Int {
        return calculateMargin(longRace)
    }
}

fun main() {
    val input = readInput("Day06")
    Day06(input).part1().println()
    Day06(input).part2().println()
}