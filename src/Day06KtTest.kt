import org.junit.Assert
import org.junit.Test

class Day06KtTest {
    private val input = readInput("Day06_test")

    @Test
    fun testInputPart1() {
        Assert.assertEquals(288, Day06(input).part1())
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(71503, Day06(input).part2())
    }
}