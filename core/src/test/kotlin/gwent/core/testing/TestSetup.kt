package gwent.core.testing

import gwent.core.game.Card

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
}