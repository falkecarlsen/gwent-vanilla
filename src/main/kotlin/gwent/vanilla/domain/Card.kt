package gwent.vanilla.domain

/**
 * The four suits of a standard game of cards.
 */
enum class Suit {
    SPADES, DIAMONDS, CLUBS, HEARTS;

    /**
     * Convert to [RowSuit], or null of [HEARTS].
     */
    fun toRowSuit(): RowSuit? {
        return when (this) {
            SPADES -> RowSuit.SPADES
            DIAMONDS -> RowSuit.DIAMONDS
            CLUBS -> RowSuit.CLUBS
            HEARTS -> null
        }
    }
}

/**
 * The three suits which have a dedicated row.
 */
enum class RowSuit {
    SPADES, DIAMONDS, CLUBS;

    /**
     * Convert to [Suit].
     */
    fun toSuit(): Suit {
        return when (this) {
            SPADES -> Suit.SPADES
            DIAMONDS -> Suit.DIAMONDS
            CLUBS -> Suit.CLUBS
        }
    }
}

/**
 * A card equivalent to its physical counterpart.
 */
sealed class Card(
    val id: String,
    val basePower: Int,
    val suit: Suit,
) {
    object Spades3 : Card("S3", 3, Suit.SPADES)
    object Spades4 : Card("S4", 4, Suit.SPADES)
    object Spades5 : Card("S5", 5, Suit.SPADES)
    object Spades6 : Card("S6", 6, Suit.SPADES)
    object Spades7 : Card("S7", 7, Suit.SPADES)
    object Spades8 : Card("S8", 8, Suit.SPADES)
    object Spades9 : Card("S9", 9, Suit.SPADES)
    object Spades10 : Card("CS0", 10, Suit.SPADES)
    object SpadesJack : Card("SJ", 11, Suit.SPADES)
    object SpadesQueen : Card("SQ", 12, Suit.SPADES)
    object SpadesKing : Card("SK", 13, Suit.SPADES)

    object Diamond3 : Card("D3", 3, Suit.DIAMONDS)
    object Diamond4 : Card("D4", 4, Suit.DIAMONDS)
    object Diamond5 : Card("D5", 5, Suit.DIAMONDS)
    object Diamond6 : Card("D6", 6, Suit.DIAMONDS)
    object Diamond7 : Card("D7", 7, Suit.DIAMONDS)
    object Diamond8 : Card("D8", 8, Suit.DIAMONDS)
    object Diamond9 : Card("D9", 9, Suit.DIAMONDS)
    object Diamond10 : Card("CD0", 10, Suit.DIAMONDS)
    object DiamondJack : Card("DJ", 11, Suit.DIAMONDS)
    object DiamondQueen : Card("DQ", 12, Suit.DIAMONDS)
    object DiamondKing : Card("DK", 13, Suit.DIAMONDS)

    object Clubs3 : Card("C3", 3, Suit.CLUBS)
    object Clubs4 : Card("C4", 4, Suit.CLUBS)
    object Clubs5 : Card("C5", 5, Suit.CLUBS)
    object Clubs6 : Card("C6", 6, Suit.CLUBS)
    object Clubs7 : Card("C7", 7, Suit.CLUBS)
    object Clubs8 : Card("C8", 8, Suit.CLUBS)
    object Clubs9 : Card("C9", 9, Suit.CLUBS)
    object Clubs10 : Card("C10", 10, Suit.CLUBS)
    object ClubsJack : Card("CJ", 11, Suit.CLUBS)
    object ClubsQueen : Card("CQ", 12, Suit.CLUBS)
    object ClubsKing : Card("CK", 13, Suit.CLUBS)

    companion object {
        /**
         * Collection contain all cards.
         */
        val ALL = listOf(
            Spades3,
            Spades4,
            Spades5,
            Spades6,
            Spades7,
            Spades8,
            Spades9,
            Spades10,
            SpadesJack,
            SpadesQueen,
            SpadesKing,
            Diamond3,
            Diamond4,
            Diamond5,
            Diamond6,
            Diamond7,
            Diamond8,
            Diamond9,
            Diamond10,
            DiamondJack,
            DiamondQueen,
            DiamondKing,
            Clubs3,
            Clubs4,
            Clubs5,
            Clubs6,
            Clubs7,
            Clubs8,
            Clubs9,
            Clubs10,
            ClubsJack,
            ClubsQueen,
            ClubsKing,
        )

        fun fromID(id: String) = ALL.first { it.id == id }
        fun fromIDOrNull(id: String) = ALL.firstOrNull { it.id == id }
    }
}

/**
 * Returns a shuffled deck with all cards.
 */
fun newDeck(): MutableList<Card> {
    return Card.ALL.toMutableList().also { it.shuffle() }
}