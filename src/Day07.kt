class Day07(private val input: List<String> = emptyList()) {

    enum class Card {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    enum class HandStrength {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }

    class CardHand(val cards: List<Card>, val bid: Int) : Comparable<CardHand> {
        fun getStrength(): HandStrength {
            val grouped = this.cards.groupingBy { it.name }.eachCount().entries.sortedByDescending { it.value }.take(2)
            return when {
                grouped.first().value == 5 -> HandStrength.FIVE_OF_A_KIND
                grouped.first().value == 4 -> HandStrength.FOUR_OF_A_KIND
                grouped.first().value == 3 && grouped[1].value == 2 -> HandStrength.FULL_HOUSE
                grouped.first().value == 3 -> HandStrength.THREE_OF_A_KIND
                grouped.first().value == 2 && grouped[1].value == 2 -> HandStrength.TWO_PAIR
                grouped.first().value == 2 -> HandStrength.PAIR
                else -> HandStrength.HIGH_CARD
            }
        }

        override fun compareTo(other: CardHand): Int = when {
            this.getStrength() > other.getStrength() -> 1
            this.getStrength() < other.getStrength() -> -1
            else -> {
                val zipped = this.cards.zip(other.cards)
                val decider = zipped.dropWhile { it.first == it.second }
                when {
                    decider.isEmpty() -> 0
                    decider.first().first > decider.first().second -> 1
                    else -> -1
                }
            }
        }
    }

    fun parseInput(): List<CardHand> {
        fun charToCard(c: Char): Card {
            return when (c) {
                '2' -> Card.TWO
                '3' -> Card.THREE
                '4' -> Card.FOUR
                '5' -> Card.FIVE
                '6' -> Card.SIX
                '7' -> Card.SEVEN
                '8' -> Card.EIGHT
                '9' -> Card.NINE
                'T' -> Card.TEN
                'J' -> Card.JACK
                'Q' -> Card.QUEEN
                'K' -> Card.KING
                'A' -> Card.ACE
                else -> throw IllegalArgumentException("Unexpected Card Value")
            }
        }

        fun parseHand(s: String): CardHand {
            val parts = s.split(" ")
            val cards = parts.first().map { charToCard(it) }
            val bid = parts.last().toInt()
            return CardHand(cards, bid)
        }
        return input.map { parseHand(it) }
    }

    val hands = parseInput()

    fun part1(): Int {
        val sorted = hands.sorted()
        val winnings = sorted.withIndex().map { (index, value) -> (index + 1) * value.bid } // rank = index + 1
        return winnings.sum()
    }

    fun part2(): Int {
        TODO()
    }
}

fun main() {
    val input = readInput("Day07")
    Day07(input).part1().println()
    Day07(input).part2().println()
}