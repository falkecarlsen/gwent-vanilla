package gwent.core.game

import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayCardTest {
    @Test
    fun playCard01() {
        // Check if playing a card works
        val game = Game("Alice", "Bob", TestSetup.variedDeck(), 0)
        val action = PlayCard(0, Card.Diamond4)

        assert(game.players[0].hand.contains(Card.Diamond4))
        assert(game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())

        game.tryPerformAction(action)

        assert(!game.players[0].hand.contains(Card.Diamond4))
        assert(Card.Diamond4 in game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards)
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())

        assertEquals(1, game.currentPlayer)
    }

    @Test
    fun playCard02() {
        // Check that it is not possible to play a card you do not have
        val game = Game("Alice", "Bob", TestSetup.variedDeck(), 0)
        val action = PlayCard(0, Card.Clubs4)

        assert(!game.players[0].hand.contains(Card.Clubs4))
        assertFailsWith(NotInHandException::class) {
            game.tryPerformAction(action)
        }
    }
}