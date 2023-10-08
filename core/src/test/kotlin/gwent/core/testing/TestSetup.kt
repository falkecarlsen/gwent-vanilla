package gwent.core.testing

import gwent.core.game.Card
import gwent.core.game.CardType
import gwent.core.game.INIT_HAND_SIZE

/**
 * Various utils for setting up test scenarios quickly.
 */
object TestSetup {

    /**
     * Construct a deck such that each player starts with some specific cards in their hands and
     * such that the top of the deck (the cards drawn next) are a specific sequence of cards.
     */
    fun stackedDeck(
        p0Cards: List<CardType> = listOf(),
        p1Cards: List<CardType> = listOf(),
        topOfDeck: List<CardType> = listOf()
    ): MutableList<Card> {
        if (p0Cards.size > INIT_HAND_SIZE) throw IllegalArgumentException("Player 0 cannot have more than $INIT_HAND_SIZE cards in their hand initially.")
        if (p1Cards.size > INIT_HAND_SIZE) throw IllegalArgumentException("Player 1 cannot have more than $INIT_HAND_SIZE cards in their hand initially.")
        val provided = p0Cards + p1Cards + topOfDeck
        if (provided.toSet().size != provided.size) throw IllegalArgumentException("Player 0's hand, player 1's hand, and the top of the deck must be distinct.")

        val rest = CardType.entries.subtract(provided.toSet()).toMutableList()
        val deck = mutableListOf<CardType>()

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

        return deck.map { Card(it) }.toMutableList()
    }
}