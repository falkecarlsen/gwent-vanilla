package gwent.core.game

import gwent.core.game.CardType.*
import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GameTest {
    @Test
    fun setup01() {
        // Test if initial card dealing is correct
        val game = Game("Alice", "Bob")
        assertEquals(game.deck.size, CARDS - 2 * INIT_HAND_SIZE)
        assertEquals(game.players[0].hand.size, INIT_HAND_SIZE)
        assertEquals(game.players[1].hand.size, INIT_HAND_SIZE)
    }

    @Test
    fun turnOrder01() {
        // Check that you cannot take actions on other the players' turn
        val p1 = 0
        val p2 = 1
        val game = Game("Alice", "Bob", currentPlayer = p1)
        assertFailsWith(OtherPlayersTurnException::class) {
            game.tryPerformAction(Pass(p2))
        }
        game.tryPerformAction(Pass(p1))

        assertEquals(p2, game.currentPlayer)
        assertFailsWith(OtherPlayersTurnException::class) {
            game.tryPerformAction(Pass(p1))
        }
    }

    @Test
    fun autoPass01() {
        // Check if player 0 auto-passes when hand becomes empty
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(SK, DK, CK, SQ, SJ, DJ, CJ, S9, D9, C9),
        )
        val game = Game("Alice", "Bob", deck)
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)

            if (game.currentPlayer == 0) {
                val card = game.players[0].hand.first()
                val row = if (card.type.suit == Suit.HEARTS) RowSuit.DIAMONDS else null
                game.tryPerformAction(PlayCard(0, card.type, row))
            } else
                game.tryPerformAction(Pass(1))
        }
        assert(limit > 0)
    }

    @Test
    fun autoPass02() {
        // Check if player 1 auto-passes when hand becomes empty
        val deck = TestSetup.stackedDeck(
            p1Cards = listOf(SK, DK, CK, SQ, SJ, DJ, CJ, S9, D9, C9),
        )
        val game = Game("Alice", "Bob", deck)
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)

            if (game.currentPlayer == 0)
                game.tryPerformAction(Pass(0))
            else
                game.tryPerformAction(PlayCard(1, game.players[1].hand.first().type, null))
        }
        assert(limit > 0)
    }

    @Test
    fun autoEnd01() {
        // Check if game auto-ends when both players have empty hands
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(S5, D5, C5, H5, S4, D4, C4, S7, D7, C7),
            p1Cards = listOf(SK, DK, CK, SQ, SJ, DJ, CJ, S9, D9, C9),
        )
        val game = Game("Alice", "Bob", deck)
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)
            assert(game.round == 0) // Other rounds are skipped


            val card = game.players[game.currentPlayer].hand.first()
            val row = RowSuit.SPADES.takeIf { card.type.suit == Suit.HEARTS }
            game.tryPerformAction(PlayCard(game.currentPlayer, card.type, row))
        }
        assert(limit > 0)
    }

    @Test
    fun postRoundCleanup01() {
        // Check if board, passed flags, and round variables are updated correctly when round ends
        val deck = TestSetup.stackedDeck(listOf(D4), listOf(C5))
        val game = Game("Alice", "Bob", deck, 0)
        game.tryPerformAction(PlayCard(0, D4, null))
        game.tryPerformAction(PlayCard(1, C5, null))

        game.tryPerformAction(Pass(0))

        assertEquals(0, game.round)
        assert(game.players[0].hasPassed)
        assert(!game.players[1].hasPassed)
        assertEquals(4, game.players[0].board.currentPower)
        assertEquals(5, game.players[1].board.currentPower)

        game.tryPerformAction(Pass(1))

        assertEquals(1, game.round)
        assert(!game.players[0].hasPassed)
        assert(!game.players[1].hasPassed)
        assertEquals(0, game.players[0].board.currentPower)
        assertEquals(0, game.players[1].board.currentPower)
    }
}