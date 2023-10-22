package gwent.core.game

import gwent.core.game.CardType.*
import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayCardTest {
    @Test
    fun playCard01() {
        // Check if playing a card works
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(D4)), 0)
        val action = PlayCard(0, D4, null)
        val d4 = game.cards.find { it.type == D4 }!!

        assert(game.players[0].hand.contains(d4))
        assert(game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())

        game.tryPerformAction(action)

        assert(!game.players[0].hand.contains(d4))
        assert(d4 in game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards)
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())

        assertEquals(1, game.currentPlayer)
    }

    @Test
    fun playCard02() {
        // Check that it is not possible to play a card you do not have
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p1Cards = listOf(C4)), 0)
        val action = PlayCard(0, C4, null)

        assert(!game.players[0].hand.any { it.type == C4 })
        assertFailsWith(NotInHandException::class) {
            game.tryPerformAction(action)
        }
    }

    @Test
    fun playCard03() {
        // Check if playing a wild card works
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(H7)), 0)
        val action = PlayCard(0, H7, RowSuit.DIAMONDS)
        val h7 = game.cards.find { it.type == H7 }!!

        assert(game.players[0].hand.contains(h7))
        assert(game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards.isEmpty())

        game.tryPerformAction(action)

        assert(!game.players[0].hand.contains(h7))
        assert(h7 in game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards)
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())
    }

    @Test
    fun playCard04() {
        // Check if you can play a wild card without specifying row
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(H9)), 0)
        val action = PlayCard(0, H9, null)

        assert(game.players[0].hand.any { it.type == H9 })
        assertFailsWith(MissingRowParameterException::class) {
            game.tryPerformAction(action)
        }
    }

    @Test
    fun playCard05() {
        // Non-wild cards must be played in the row matching their suit
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(C4)), 0)
        val action = PlayCard(0, C4, RowSuit.DIAMONDS)

        assert(game.players[0].hand.any { it.type == C4 })
        assertFailsWith(InvalidRowParameterException::class) {
            game.tryPerformAction(action)
        }
    }
}