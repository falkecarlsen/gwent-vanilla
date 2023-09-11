package gwent.core.game

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
        val game = Game("Alice", "Bob")
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)

            if (game.currentPlayer == 0) {
                val card = game.players[0].hand.first()
                val row = if (card.suit == Suit.HEARTS) RowSuit.DIAMONDS else null
                game.tryPerformAction(PlayCard(0, card, row))
            } else
                game.tryPerformAction(Pass(1))
        }
        assert(limit > 0)
    }

    @Test
    fun autoPass02() {
        // Check if player 1 auto-passes when hand becomes empty
        val deck = TestSetup.stackedDeck(
            p1Cards = listOf(Card.SpadesKing, Card.DiamondKing, Card.ClubsKing, Card.SpadesQueen,
                Card.SpadesJack, Card.DiamondJack, Card.ClubsJack, Card.Spades9, Card.Diamond9, Card.Clubs9),
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
                game.tryPerformAction(PlayCard(1, game.players[1].hand.first(), null))
        }
        assert(limit > 0)
    }

    @Test
    fun autoEnd01() {
        // Check if game auto-ends when both players have empty hands
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(Card.Spades5, Card.Diamond5, Card.Clubs5, Card.Hearts3, Card.Spades4, Card.Diamond4,
                Card.Clubs4, Card.Spades7, Card.Diamond7, Card.Clubs7),
            p1Cards = listOf(Card.SpadesKing, Card.DiamondKing, Card.ClubsKing, Card.SpadesQueen,
                Card.SpadesJack, Card.DiamondJack, Card.ClubsJack, Card.Spades9, Card.Diamond9, Card.Clubs9),
        )
        val game = Game("Alice", "Bob", deck)
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)
            assert(game.round == 0) // Other rounds are skipped


            val card = game.players[game.currentPlayer].hand.first()
            val row = RowSuit.SPADES.takeIf { card.suit == Suit.HEARTS }
            game.tryPerformAction(PlayCard(game.currentPlayer, card, row))
        }
        assert(limit > 0)
    }

    @Test
    fun postRoundCleanup01() {
        // Check if board, passed flags, and round variables are updated correctly when round ends
        val deck = TestSetup.stackedDeck(listOf(Card.Diamond4), listOf(Card.Clubs5))
        val game = Game("Alice", "Bob", deck, 0)
        game.tryPerformAction(PlayCard(0, Card.Diamond4, null))
        game.tryPerformAction(PlayCard(1, Card.Clubs5, null))

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