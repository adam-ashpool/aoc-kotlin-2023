import kotlin.math.min
import kotlin.math.pow

class Day04(private val input: List<String> = emptyList()) {
    private val inputSize = input.size

    data class ScratchCard(
        val id: Int,
        val winningNumbers: List<Int>,
        val drawnNumbers: List<Int>,
        var numberOfCopies: Int
    )

    fun parseLine(line: String): ScratchCard {
        val parts = line.split(":", "|")
        val id = parts[0].split("\\s+".toRegex())[1].toInt()
        val winningNumbers = parts[1].trim().split("\\s+".toRegex()).map { it.toInt() }
        val drawnNumbers = parts[2].trim().split("\\s+".toRegex()).map { it.toInt() }
        return ScratchCard(id, winningNumbers, drawnNumbers, 1)
    }

    private fun calculateNumberOfMatches(card: ScratchCard): Int {
        return card.drawnNumbers.filter { card.winningNumbers.contains(it) }.size
    }

    fun calculatePoints(card: ScratchCard): Int {
        val matches = calculateNumberOfMatches(card)
        return if (matches == 0) 0 else 2.toDouble().pow(matches - 1).toInt()
    }

    fun part1(): Int {
        return input.map { parseLine(it) }.sumOf { calculatePoints(it) }
    }

    /***
     * Evaluates all scratch cards recursively and returns the final number of copies of each card.
     */
    private fun processScratchCards(cards: List<ScratchCard>, index: Int): List<ScratchCard> {
        return when (index) {
            inputSize -> cards
            else -> {
                val matches = calculateNumberOfMatches(cards[index])
                if (matches > 0) {
                    for (i in (index + 1..min(index + matches, inputSize - 1))) {
                        cards[i].numberOfCopies += cards[index].numberOfCopies
                    }
                }
                return processScratchCards(cards, index + 1)
            }
        }
    }

    fun part2(): Int {
        val processedCards = processScratchCards(input.map { parseLine(it) }, 0)
        return processedCards.sumOf { it.numberOfCopies }
    }
}

fun main() {
    val input = readInput("Day04")
    Day04(input).part1().println()
    Day04(input).part2().println()
}