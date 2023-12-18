class Day02 {
    data class Record(val id: Id, val games: List<Game>)
    data class Game(val red: Int, val green: Int, val blue: Int)
    @JvmInline
    value class Id(val n: Int)

    fun parseLine(s: String): Record {
        fun parseGame(g: String): Game {
            val redRegex = Regex("((?<red>\\d+) red)")
            val greenRegex = Regex("((?<green>\\d+) green)")
            val blueRegex = Regex("((?<blue>\\d+) blue)")
            val red = (redRegex.find(g)?.groups?.get("red")?.value ?: "0").toInt()
            val green = (greenRegex.find(g)?.groups?.get("green")?.value ?: "0").toInt()
            val blue = (blueRegex.find(g)?.groups?.get("blue")?.value ?: "0").toInt()
            return Game(red, green, blue)
        }

        val id = Id(s.split(":").first().replace("Game ", "").toInt())
        val games = s.split(":").last().split(";").map { parseGame(it) }
        return Record(id, games)
    }

    private fun isRecordPossible(games: List<Game>, limit: Game): Boolean {
        fun isGamePossible(game: Game): Boolean {
            return game.red <= limit.red && game.green <= limit.green && game.blue <= limit.blue
        }
        return games.all { isGamePossible(it) }
    }

    /***
     * Return a Game with the least amount of balls to satisfy all games in the list
     */
    private fun minimumGame(games: List<Game>): Game {
        return Game(games.map { it.red }.max(), games.map { it.green }.max(), games.map { it.blue }.max())
    }

    fun part1(input: List<String>): Int {
        val limit = Game(12, 13, 14)
        val gameRecords = input.map { parseLine(it) }
        val possibleGameRecords = gameRecords.filter { isRecordPossible(it.games, limit) }
        return possibleGameRecords.sumOf { it.id.n }
    }

    fun part2(input: List<String>): Int {
        val gameRecords = input.map { parseLine(it) }
        val minimalGamesPowers = gameRecords.map { minimumGame(it.games) }.map { it.red * it.green * it.blue }
        return minimalGamesPowers.sum()
    }
}

fun main() {
    val input = readInput("Day02")
    Day02().part1(input).println()
    Day02().part2(input).println()
}