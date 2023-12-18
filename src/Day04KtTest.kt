import org.junit.Assert
import org.junit.Test

class Day04KtTest {
    private val input = readInput("Day04_test")

    @Test
    fun testInputPart1() {
        Assert.assertEquals(13, Day04(input).part1())
    }

    @Test
    fun testParseLine() {
        val line = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val expectedScratchCard =
            Day04.ScratchCard(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53), 1)
        Assert.assertEquals(expectedScratchCard, Day04().parseLine(line))
    }

    @Test
    fun testCalculatePoints() {
        val scratchCard = Day04.ScratchCard(1, listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53), 1)
        Assert.assertEquals(8, Day04().calculatePoints(scratchCard))
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(30, Day04(input).part2())
    }
}