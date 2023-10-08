package gwent.core.game

import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayCardTest {
    @Test
    fun playCard01() {
        // Check if playing a card works
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(Card.Diamond4)), 0)
        val action = PlayCard(0, Card.Diamond4, null)

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
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p1Cards = listOf(Card.Clubs4)), 0)
        val action = PlayCard(0, Card.Clubs4, null)

        assert(!game.players[0].hand.contains(Card.Clubs4))
        assertFailsWith(NotInHandException::class) {
            game.tryPerformAction(action)
        }
    }

    @Test
    fun playCard03() {
        // Check if playing a wild card works
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(Card.Hearts7)), 0)
        val action = PlayCard(0, Card.Hearts7, RowSuit.DIAMONDS)

        assert(game.players[0].hand.contains(Card.Hearts7))
        assert(game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards.isEmpty())

        game.tryPerformAction(action)

        assert(!game.players[0].hand.contains(Card.Hearts7))
        assert(Card.Hearts7 in game.players[0].board.rows[RowSuit.DIAMONDS]!!.cards)
        assert(game.players[0].board.rows[RowSuit.CLUBS]!!.cards.isEmpty())
        assert(game.players[0].board.rows[RowSuit.SPADES]!!.cards.isEmpty())
    }

    @Test
    fun playCard04() {
        // Check if you can play a wild card without specifying row
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(Card.Hearts9)), 0)
        val action = PlayCard(0, Card.Hearts9, null)

        assert(game.players[0].hand.contains(Card.Hearts9))
        assertFailsWith(MissingRowParameterException::class) {
            game.tryPerformAction(action)
        }
    }

    @Test
    fun playCard05() {
        // Non-wild cards must be played in the row matching their suit
        val game = Game("Alice", "Bob", TestSetup.stackedDeck(p0Cards = listOf(Card.Clubs4)), 0)
        val action = PlayCard(0, Card.Clubs4, RowSuit.DIAMONDS)

        assert(game.players[0].hand.contains(Card.Clubs4))
        assertFailsWith(InvalidRowParameterException::class) {
            game.tryPerformAction(action)
        }
    }
}