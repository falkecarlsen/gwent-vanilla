package gwent.core.game

import gwent.core.game.abilities.*
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
 * A card type is a collection of static attributes for cards.
 * Every type has a unique name and a unique pair of suit and numeric value.
 */
enum class CardType(
    val numeric: Int,
    val suit: Suit,
    val basePower: Int,
    val tags: List<Tag> = listOf(),
    val ongoing: OngoingEffect? = null,
    val immediate: ImmediateEffect? = null,
) {
    H3(3, Suit.HEARTS, 3, listOf(Tag.Unit, Tag.Wild, Tag.Spy), null, DrawCardsImmediateEffect()),
    H4(4, Suit.HEARTS, 0, listOf(Tag.Unit, Tag.Wild, Tag.Militia), MilitiaOngoingEffect()),
    H5(5, Suit.HEARTS, 5, listOf(Tag.Unit, Tag.Wild)),
    H6(6, Suit.HEARTS, 6, listOf(Tag.Unit, Tag.Wild)),
    H7(7, Suit.HEARTS, 7, listOf(Tag.Unit, Tag.Wild), CavalryOngoingEffect()),
    H8(8, Suit.HEARTS, 8, listOf(Tag.Unit, Tag.Wild)),
    H9(9, Suit.HEARTS, 9, listOf(Tag.Unit, Tag.Wild)),
    H10(10, Suit.HEARTS, 10, listOf(Tag.Unit, Tag.Wild)),
    HJ(11, Suit.HEARTS, 0, listOf(Tag.Unit, Tag.Wild), CaptainOngoingEffect()),
    HQ(12, Suit.HEARTS, 10, listOf(Tag.Unit, Tag.Wild, Tag.Queen)),
    HK(13, Suit.HEARTS, 10, listOf(Tag.Unit, Tag.Wild)),

    S3(3, Suit.SPADES, 3, listOf(Tag.Unit, Tag.Spy), null, DrawCardsImmediateEffect()),
    S4(4, Suit.SPADES, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect()),
    S5(5, Suit.SPADES, 5, listOf(Tag.Unit)),
    S6(6, Suit.SPADES, 6, listOf(Tag.Unit)),
    S7(7, Suit.SPADES, 7, listOf(Tag.Unit), CavalryOngoingEffect()),
    S8(8, Suit.SPADES, 8, listOf(Tag.Unit)),
    S9(9, Suit.SPADES, 9, listOf(Tag.Unit)),
    S10(10, Suit.SPADES, 10, listOf(Tag.Unit)),
    SJ(11, Suit.SPADES, 0, listOf(Tag.Unit), CaptainOngoingEffect()),
    SQ(12, Suit.SPADES, 10, listOf(Tag.Unit, Tag.Queen)),
    SK(13, Suit.SPADES, 10, listOf(Tag.Unit)),

    D3(3, Suit.DIAMONDS, 3, listOf(Tag.Unit, Tag.Spy), null, DrawCardsImmediateEffect()),
    D4(4, Suit.DIAMONDS, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect()),
    D5(5, Suit.DIAMONDS, 5, listOf(Tag.Unit)),
    D6(6, Suit.DIAMONDS, 6, listOf(Tag.Unit)),
    D7(7, Suit.DIAMONDS, 7, listOf(Tag.Unit), CavalryOngoingEffect()),
    D8(8, Suit.DIAMONDS, 8, listOf(Tag.Unit)),
    D9(9, Suit.DIAMONDS, 9, listOf(Tag.Unit)),
    D10(10, Suit.DIAMONDS, 10, listOf(Tag.Unit)),
    DJ(11, Suit.DIAMONDS, 0, listOf(Tag.Unit), CaptainOngoingEffect()),
    DQ(12, Suit.DIAMONDS, 10, listOf(Tag.Unit, Tag.Queen)),
    DK(13, Suit.DIAMONDS, 10, listOf(Tag.Unit)),

    C3(3, Suit.CLUBS, 3, listOf(Tag.Unit, Tag.Spy), null, DrawCardsImmediateEffect()),
    C4(4, Suit.CLUBS, 0, listOf(Tag.Unit, Tag.Militia), MilitiaOngoingEffect()),
    C5(5, Suit.CLUBS, 5, listOf(Tag.Unit)),
    C6(6, Suit.CLUBS, 6, listOf(Tag.Unit)),
    C7(7, Suit.CLUBS, 7, listOf(Tag.Unit), CavalryOngoingEffect()),
    C8(8, Suit.CLUBS, 8, listOf(Tag.Unit)),
    C9(9, Suit.CLUBS, 9, listOf(Tag.Unit)),
    C10(10, Suit.CLUBS, 10, listOf(Tag.Unit)),
    CJ(11, Suit.CLUBS, 0, listOf(Tag.Unit), CaptainOngoingEffect()),
    CQ(12, Suit.CLUBS, 10, listOf(Tag.Unit, Tag.Queen)),
    CK(13, Suit.CLUBS, 10, listOf(Tag.Unit));

    companion object {
        fun fromName(id: String) = entries.firstOrNull() { it.name == id } ?: throw InvalidCardNameException(id)
        fun fromNameOrNull(id: String) = entries.firstOrNull { it.name == id }
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

class Card(
    val type: CardType,
) {
    /** The current power this card can contribute.
     * Equal to base power of type unless affected by abilities on the board. */
    var currentPower: Int = type.basePower

    /** The index of the player who currently "controls" this card of the card, or null if the card is in the deck. */
    var owner: Int? = null

    /** The current row the card lies in, or null if not on the board (either in hand or deck or discard pile). */
    var row: RowSuit? = null

    /**
     * Convert to data transfer object.
     */
    fun toDTO() = CardDTO(
        name = type.name,
        currentPower = currentPower,
    )
}

/**
 * Returns a new shuffled deck of cards
 */
fun newDeck(): MutableList<Card> {
    return CardType.entries.map { Card(it) }.toMutableList().also { it.shuffle() }
}
