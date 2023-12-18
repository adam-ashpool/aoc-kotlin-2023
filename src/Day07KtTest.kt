import org.junit.Assert
import org.junit.Test
import Day07.Card.*
import Day07.CardHand

class Day07KtTest {
    private val input = readInput("Day07_test")

    @Test
    fun testCompare() {
        val pair1 = CardHand(listOf(TWO, QUEEN, KING, QUEEN, SEVEN), 10)
        val pair2 = CardHand(listOf(TWO, TWO, KING, QUEEN, SEVEN), 10)
        val fourOfAKind = CardHand(listOf(KING, QUEEN, KING, KING, KING), 10)
        Assert.assertEquals(true, pair1 > pair2)
        Assert.assertEquals(false, pair1 > fourOfAKind)
    }

    @Test
    fun testInputPart1() {
        Assert.assertEquals(6440, Day07(input).part1())
    }

    @Test
    fun testInputPart2() {
        Assert.assertEquals(71503, Day07(input).part2())
    }
}