package gwent.core.testing

import gwent.core.game.Card
import gwent.core.game.INIT_HAND_SIZE

/**
 * Various utils for setting up test scenarios quickly.
 */
object TestSetup {

    fun variedDeck() = mutableListOf(
        // P1 HAND
        Card.Spades3,
        Card.Diamond6,
        Card.Diamond4,
        Card.Clubs7,
        Card.ClubsJack,
        Card.Diamond9,
        Card.SpadesKing,
        Card.Diamond5,
        Card.ClubsQueen,
        Card.Spades4,
        // P2 HAND
        Card.Spades10,
        Card.Spades8,
        Card.Spades6,
        Card.DiamondJack,
        Card.Clubs8,
        Card.Spades9,
        Card.Diamond7,
        Card.Clubs5,
        Card.Clubs3,
        Card.Diamond10,
        // REST
        Card.DiamondQueen,
        Card.Diamond8,
        Card.SpadesQueen,
        Card.Clubs9,
        Card.DiamondKing,
        Card.ClubsKing,
        Card.Spades7,
        Card.Clubs10,
        Card.Spades5,
        Card.SpadesJack,
        Card.Clubs6,
        Card.Diamond3,
        Card.Clubs4,
    )

    /**
     * Construct a deck such that each player starts with some specific cards in their hands and
     * such that the top of the deck (the cards drawn next) are a specific sequence of cards.
     */
    fun stackedDeck(p0Cards: List<Card>, p1Cards: List<Card>, topOfDeck: List<Card>): List<Card> {
        if (p0Cards.size > INIT_HAND_SIZE) throw IllegalArgumentException("Player 0 cannot have more than $INIT_HAND_SIZE cards in their hand initially.")
        if (p1Cards.size > INIT_HAND_SIZE) throw IllegalArgumentException("Player 1 cannot have more than $INIT_HAND_SIZE cards in their hand initially.")
        val provided = p0Cards + p1Cards + topOfDeck
        if (provided.toSet().size != provided.size) throw IllegalArgumentException("Player 0's hand, player 1's hand, and the top of the deck must be distinct.")

        val rest = Card.all().subtract(provided.toSet()).toMutableList()
        val deck = mutableListOf<Card>()

        // Build player 0's hand
        deck += p0Cards
        while (deck.size < INIT_HAND_SIZE) {
            deck += rest.removeFirst()
        }

        // Build player 1's hand
        deck += p1Cards
        while (deck.size < INIT_HAND_SIZE * 2) {
            deck += rest.removeFirst()
        }

        // Build rest of deck
        deck += topOfDeck
        deck += rest

        return deck
    }
}