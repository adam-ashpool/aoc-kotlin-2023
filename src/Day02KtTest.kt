import Day02.*
import org.junit.Assert
import org.junit.Test

class Day02KtTest {
    private val input = readInput("Day02_test")

    @Test
    fun testInputPart1() {
        Assert.assertEquals(8, Day02().part1(input))
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(2286, Day02().part2(input))
    }

    @Test
    fun testParseLine() {
        val parsedLine = Day02().parseLine("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
        val expectedResult = Record(
            Id(1), listOf(
                Game(4, 0, 3),
                Game(1, 2, 6),
                Game(0, 2, 0)
            )
        )
        Assert.assertEquals(expectedResult, parsedLine)
    }
}