import org.junit.Assert
import org.junit.Test

class Day03KtTest {
    private val input = readInput("Day03_test")

    @Test
    fun testInputPart1() {
        Assert.assertEquals(4361, Day03(input).part1())
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(467835, Day03(input).part2())
    }
}