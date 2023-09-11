package gwent.core.game.abilities

import gwent.core.game.*
import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals

class SpiesTest {
    @Test
    fun spies01() {
        // Test if spies are placed on opponents board
        val initDeck = TestSetup.stackedDeck(listOf(Card.Clubs3), listOf(Card.Spades3), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        game.tryPerformAction(PlayCard(0, Card.Clubs3, null))

        assert(Card.Clubs3 in game.players[1].board.rows[RowSuit.CLUBS]!!.cards)
        assertEquals(3, game.players[1].board.currentPower)
        assertEquals(1, Card.Clubs3.owner)

        game.tryPerformAction(PlayCard(1, Card.Spades3, null))

        assert(Card.Spades3 in game.players[0].board.rows[RowSuit.SPADES]!!.cards)
        assertEquals(3, game.players[0].board.currentPower)
        assertEquals(0, Card.Spades3.owner)
    }

    @Test
    fun spies02() {
        // Test if spies draws a card (for the correct player)
        val initDeck = TestSetup.stackedDeck(
            listOf(Card.Clubs3),
            listOf(Card.Spades3),
            listOf(Card.DiamondQueen, Card.DiamondKing)
        )
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        assertEquals(INIT_HAND_SIZE, game.players[0].hand.size)
        assertEquals(INIT_HAND_SIZE, game.players[1].hand.size)
        assertEquals(Card.DiamondQueen, game.deck[0])
        assertEquals(Card.DiamondKing, game.deck[1])

        game.tryPerformAction(PlayCard(0, Card.Clubs3, null))

        assertEquals(INIT_HAND_SIZE, game.players[0].hand.size)
        assert(Card.DiamondQueen in game.players[0].hand)

        game.tryPerformAction(PlayCard(1, Card.Spades3, null))

        assertEquals(INIT_HAND_SIZE, game.players[1].hand.size)
        assert(Card.DiamondKing in game.players[1].hand)
    }
}