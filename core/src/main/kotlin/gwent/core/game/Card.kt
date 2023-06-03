package gwent.core.game

import gwent.core.game.abilities.OngoingEffect
import gwent.core.game.abilities.CaptainOngoingEffect
import gwent.core.game.abilities.CavalryOngoingEffect
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

    object Spades3 : Card("S3", 3, Suit.SPADES, 3, listOf(Tag.Unit))
    object Spades4 : Card("S4", 4, Suit.SPADES, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect())
    object Spades5 : Card("S5", 5, Suit.SPADES, 5, listOf(Tag.Unit))
    object Spades6 : Card("S6", 6, Suit.SPADES, 6, listOf(Tag.Unit))
    object Spades7 : Card("S7", 7, Suit.SPADES, 7, listOf(Tag.Unit), CavalryOngoingEffect())
    object Spades8 : Card("S8", 8, Suit.SPADES, 8, listOf(Tag.Unit))
    object Spades9 : Card("S9", 9, Suit.SPADES, 9, listOf(Tag.Unit))
    object Spades10 : Card("S10", 10, Suit.SPADES, 10, listOf(Tag.Unit))
    object SpadesJack : Card("SJ", 11, Suit.SPADES, 0, listOf(Tag.Unit), CaptainOngoingEffect())
    object SpadesQueen : Card("SQ", 12, Suit.SPADES, 10, listOf(Tag.Unit, Tag.Queen))
    object SpadesKing : Card("SK", 13, Suit.SPADES, 10, listOf(Tag.Unit))

    object Diamond3 : Card("D3", 3, Suit.DIAMONDS, 3, listOf(Tag.Unit))
    object Diamond4 : Card("D4", 4, Suit.DIAMONDS, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect())
    object Diamond5 : Card("D5", 5, Suit.DIAMONDS, 5, listOf(Tag.Unit))
    object Diamond6 : Card("D6", 6, Suit.DIAMONDS, 6, listOf(Tag.Unit))
    object Diamond7 : Card("D7", 7, Suit.DIAMONDS, 7, listOf(Tag.Unit), CavalryOngoingEffect())
    object Diamond8 : Card("D8", 8, Suit.DIAMONDS, 8, listOf(Tag.Unit))
    object Diamond9 : Card("D9", 9, Suit.DIAMONDS, 9, listOf(Tag.Unit))
    object Diamond10 : Card("D10", 10, Suit.DIAMONDS, 10, listOf(Tag.Unit))
    object DiamondJack : Card("DJ", 11, Suit.DIAMONDS, 0, listOf(Tag.Unit), CaptainOngoingEffect())
    object DiamondQueen : Card("DQ", 12, Suit.DIAMONDS, 10, listOf(Tag.Unit, Tag.Queen))
    object DiamondKing : Card("DK", 13, Suit.DIAMONDS, 10, listOf(Tag.Unit))

    object Clubs3 : Card("C3", 3, Suit.CLUBS, 3, listOf(Tag.Unit))
    object Clubs4 : Card("C4", 4, Suit.CLUBS, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect())
    object Clubs5 : Card("C5", 5, Suit.CLUBS, 5, listOf(Tag.Unit))
    object Clubs6 : Card("C6", 6, Suit.CLUBS, 6, listOf(Tag.Unit))
    object Clubs7 : Card("C7", 7, Suit.CLUBS, 7, listOf(Tag.Unit), CavalryOngoingEffect())
    object Clubs8 : Card("C8", 8, Suit.CLUBS, 8, listOf(Tag.Unit))
    object Clubs9 : Card("C9", 9, Suit.CLUBS, 9, listOf(Tag.Unit))
    object Clubs10 : Card("C10", 10, Suit.CLUBS, 10, listOf(Tag.Unit))
    object ClubsJack : Card("CJ", 11, Suit.CLUBS, 0, listOf(Tag.Unit), CaptainOngoingEffect())
    object ClubsQueen : Card("CQ", 12, Suit.CLUBS, 10, listOf(Tag.Unit, Tag.Queen))
    object ClubsKing : Card("CK", 13, Suit.CLUBS, 10, listOf(Tag.Unit))

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
 * Returns a shuffled deck with all cards.
 */
fun newDeck(): MutableList<Card> {
    return Card.all().toMutableList().also { it.shuffle() }
}