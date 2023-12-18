import org.junit.Assert
import org.junit.Test

class Day05KtTest {
    private val input = readInput("Day05_test")

    @Test
    fun testInputPart1() {
        Assert.assertEquals(35, Day05(input).part1())
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(46, Day05(input).part2())
    }
}