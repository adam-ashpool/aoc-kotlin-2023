import kotlin.math.max
import kotlin.math.min

class Day03(private val input: List<String>) {

    private val width = input.first().length
    private val height = input.size

    data class IndexedNumber(val x: Int, val y: Int, val number: Int)

    fun isNumberAdjacentToSymbol(n: IndexedNumber): Boolean {
        val minX = max(0, n.x - 1) // left boundary to check
        val maxX = min(n.x + n.number.toString().length, width - 1) // right boundary
        val minY = max(0, n.y - 1) // upper bound
        val maxY = min(n.y + 1, height - 1) // lower bound
        val isAdjacent = input.slice(minY..maxY).any { it.slice(minX..maxX).contains(Regex("[^.\\d]")) }
        return isAdjacent
    }

    /***
     * Reads a String, returns only numbers that are adjacent to a symbol
     */
    fun parseLine(lineIndex: Int, line: String): List<IndexedNumber> {
        val indexedNumbers = mutableListOf<IndexedNumber>()
        var isBuildingANumber =
            false // to know whether the next digit starts a new number or should be added to previous one
        var acc = ""
        var firstDigitIndex = 0
        for ((charIndex, c) in line.withIndex()) {
            if (c.isDigit()) {
                if (!isBuildingANumber) firstDigitIndex = charIndex
                acc += c
                isBuildingANumber = true
                if (charIndex == width - 1) { // handle numbers ending at line end
                    val indexedNumberToAdd = IndexedNumber(firstDigitIndex, lineIndex, acc.toInt())
                    if (isNumberAdjacentToSymbol(indexedNumberToAdd)) indexedNumbers.add(indexedNumberToAdd)
                }
            } else {
                if (isBuildingANumber) {
                    val indexedNumberToAdd = IndexedNumber(firstDigitIndex, lineIndex, acc.toInt())
                    if (isNumberAdjacentToSymbol(indexedNumberToAdd)) indexedNumbers.add(indexedNumberToAdd)
                    acc = ""
                    isBuildingANumber = false
                }
            }
        }
        return indexedNumbers
    }

    fun getSumOfGearRatios(numbers: List<IndexedNumber>): Int {
        fun isNumberTouching(n: IndexedNumber, x: Int, y: Int): Boolean {
            val yCondition = n.y in (y - 1..y + 1)
            val xCondition = n.x in (x - n.number.toString().length..x + 1)
            return yCondition && xCondition
        }

        fun getSumOfGearRatiosRecursive(lineIndex: Int, acc: Int): Int {
            fun getSumOfGearRationPerLine(charIndex: Int, acc: Int): Int {
                val line = input[lineIndex]
                return when {
                    charIndex == width -> acc
                    line[charIndex] == '*' -> {
                        val surroundingNumbers = numbers.filter { isNumberTouching(it, charIndex, lineIndex) }
                        if (surroundingNumbers.size == 2) {
                            val gearRatio = surroundingNumbers[0].number * surroundingNumbers[1].number
                            getSumOfGearRationPerLine(charIndex + 1, acc + gearRatio)
                        } else {
                            getSumOfGearRationPerLine(charIndex + 1, acc)
                        }
                    }

                    else -> {
                        getSumOfGearRationPerLine(charIndex + 1, acc) // char other than *
                    }
                }
            }

            return when {
                lineIndex == height -> acc
                else -> {
                    getSumOfGearRatiosRecursive(lineIndex + 1, acc + getSumOfGearRationPerLine(0, 0))
                }
            }
        }

        return getSumOfGearRatiosRecursive(0, 0)
    }

    /***
     * Finds all numbers that are adjacent to a symbol other than "." and sums them up.
     */
    fun part1(): Int {
        val indexedNumbers = mutableListOf<IndexedNumber>()
        for ((index, line) in input.withIndex()) {
            indexedNumbers.addAll(parseLine(index, line))
        }
        return indexedNumbers.sumOf { it.number }
    }

    fun part2(): Int {
        val indexedNumbers = mutableListOf<IndexedNumber>()
        for ((index, line) in input.withIndex()) {
            indexedNumbers.addAll(parseLine(index, line))
        }
        return getSumOfGearRatios(indexedNumbers)
    }
}

fun main() {
    val input = readInput("Day03")
    Day03(input).part1().println()
    Day03(input).part2().println()
}