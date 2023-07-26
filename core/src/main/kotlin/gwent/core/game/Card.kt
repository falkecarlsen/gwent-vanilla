package gwent.core.game

import gwent.core.game.abilities.OngoingEffect
import gwent.core.game.abilities.CaptainOngoingEffect
import gwent.core.game.abilities.MilitiaOngoingEffect
import gwent.core.serialize.CardDTO

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

    companion object {
        fun fromNameOrNull(name: String): Suit? {
            return when (name) {
                "spades" -> SPADES
                "diamonds" -> DIAMONDS
                "clubs" -> CLUBS
                "hearts" -> HEARTS
                else -> null
            }
        }

        fun fromName(name: String): Suit = fromNameOrNull(name) ?: throw InvalidSuitNameException(name)
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

    companion object {
        fun fromNameOrNull(name: String): RowSuit? = Suit.fromNameOrNull(name)?.toRowSuit()

        fun fromName(name: String): RowSuit = fromNameOrNull(name) ?: throw InvalidSuitNameException(name)
    }
}

/**
 * A card equivalent to its physical counterpart.
 */
sealed class Card(
    val name: String,
    val numeric: Int,
    val suit: Suit,
    val basePower: Int,
    val tags: List<Tag> = listOf(),
    val ongoing: OngoingEffect? = null,
) {
    var currentPower: Int = basePower
    var owner: Int? = null
    var row: RowSuit? = null

    object Hearts3 : Card("H3", 3, Suit.HEARTS, 3, listOf(Tag.Wild))
    object Hearts4 : Card("H4", 4, Suit.HEARTS, 0, listOf(Tag.Wild, Tag.Militia), MilitiaOngoingEffect())
    object Hearts5 : Card("H5", 5, Suit.HEARTS, 5, listOf(Tag.Wild))
    object Hearts6 : Card("H6", 6, Suit.HEARTS, 6, listOf(Tag.Wild))
    object Hearts7 : Card("H7", 7, Suit.HEARTS, 7, listOf(Tag.Wild))
    object Hearts8 : Card("H8", 8, Suit.HEARTS, 8, listOf(Tag.Wild))
    object Hearts9 : Card("H9", 9, Suit.HEARTS, 9, listOf(Tag.Wild))
    object Hearts10 : Card("H10", 10, Suit.HEARTS, 10, listOf(Tag.Wild))
    object HeartsJack : Card("HJ", 11, Suit.HEARTS, 0, listOf(Tag.Wild), CaptainOngoingEffect())
    object HeartsQueen : Card("HQ", 12, Suit.HEARTS, 10, listOf(Tag.Wild))
    object HeartsKing : Card("HK", 13, Suit.HEARTS, 10, listOf(Tag.Wild))

    object Spades3 : Card("S3", 3, Suit.SPADES, 3)
    object Spades4 : Card("S4", 4, Suit.SPADES, 0, listOf(Tag.Militia), MilitiaOngoingEffect())
    object Spades5 : Card("S5", 5, Suit.SPADES, 5)
    object Spades6 : Card("S6", 6, Suit.SPADES, 6)
    object Spades7 : Card("S7", 7, Suit.SPADES, 7)
    object Spades8 : Card("S8", 8, Suit.SPADES, 8)
    object Spades9 : Card("S9", 9, Suit.SPADES, 9)
    object Spades10 : Card("S10", 10, Suit.SPADES, 10)
    object SpadesJack : Card("SJ", 11, Suit.SPADES, 0, ongoing = CaptainOngoingEffect())
    object SpadesQueen : Card("SQ", 12, Suit.SPADES, 10)
    object SpadesKing : Card("SK", 13, Suit.SPADES, 10)

    object Diamond3 : Card("D3", 3, Suit.DIAMONDS, 3)
    object Diamond4 : Card("D4", 4, Suit.DIAMONDS, 0, listOf(Tag.Militia), MilitiaOngoingEffect())
    object Diamond5 : Card("D5", 5, Suit.DIAMONDS, 5)
    object Diamond6 : Card("D6", 6, Suit.DIAMONDS, 6)
    object Diamond7 : Card("D7", 7, Suit.DIAMONDS, 7)
    object Diamond8 : Card("D8", 8, Suit.DIAMONDS, 8)
    object Diamond9 : Card("D9", 9, Suit.DIAMONDS, 9)
    object Diamond10 : Card("D10", 10, Suit.DIAMONDS, 10)
    object DiamondJack : Card("DJ", 11, Suit.DIAMONDS, 0, ongoing = CaptainOngoingEffect())
    object DiamondQueen : Card("DQ", 12, Suit.DIAMONDS, 10)
    object DiamondKing : Card("DK", 13, Suit.DIAMONDS, 10)

    object Clubs3 : Card("C3", 3, Suit.CLUBS, 3)
    object Clubs4 : Card("C4", 4, Suit.CLUBS, 0, listOf(Tag.Militia), MilitiaOngoingEffect())
    object Clubs5 : Card("C5", 5, Suit.CLUBS, 5)
    object Clubs6 : Card("C6", 6, Suit.CLUBS, 6)
    object Clubs7 : Card("C7", 7, Suit.CLUBS, 7)
    object Clubs8 : Card("C8", 8, Suit.CLUBS, 8)
    object Clubs9 : Card("C9", 9, Suit.CLUBS, 9)
    object Clubs10 : Card("C10", 10, Suit.CLUBS, 10)
    object ClubsJack : Card("CJ", 11, Suit.CLUBS, 0, ongoing = CaptainOngoingEffect())
    object ClubsQueen : Card("CQ", 12, Suit.CLUBS, 10)
    object ClubsKing : Card("CK", 13, Suit.CLUBS, 10)

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = CardDTO(
        name = name,
        basePower = basePower,
        suit = suit.name.lowercase(),
    )

    companion object {
        /**
         * Collection contain all cards.
         */
        fun all() = listOf(
            Hearts3,
            Hearts4,
            Hearts5,
            Hearts6,
            Hearts7,
            Hearts8,
            Hearts9,
            Hearts10,
            HeartsJack,
            HeartsQueen,
            HeartsKing,
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

        fun fromName(id: String) = all().firstOrNull() { it.name == id } ?: throw InvalidCardNameException(id)
        fun fromNameOrNull(id: String) = all().firstOrNull { it.name == id }
    }
}

/**
 * Thrown when parsing card names and the given name is not valid.
 */
class InvalidCardNameException(val invalidName: String) : RuntimeException("'$invalidName' is not a valid card name")

/**
 * Thrown when parsing row and suit names if the name is not valid.
 */
class InvalidSuitNameException(val invalidName: String) : RuntimeException("'$invalidName' is not a valid suit name")

/**
 * Returns a shuffled deck with all cards.
 */
fun newDeck(): MutableList<Card> {
    return Card.all().toMutableList().also { it.shuffle() }
}