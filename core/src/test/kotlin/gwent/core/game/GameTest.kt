package gwent.core.game

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

            if (game.currentPlayer == 0)
                game.tryPerformAction(PlayCard(0, game.players[0].hand.first()))
            else
                game.tryPerformAction(Pass(1))
        }
        assert(limit > 0)
    }

    @Test
    fun autoPass02() {
        // Check if player 1 auto-passes when hand becomes empty
        val game = Game("Alice", "Bob")
        var limit = 10000
        while (!game.isGameOver() && limit > 0) {
            limit--
            assert(game.players[0].hand.isNotEmpty() || game.players[0].hasPassed)
            assert(game.players[1].hand.isNotEmpty() || game.players[1].hasPassed)

            if (game.currentPlayer == 0)
                game.tryPerformAction(Pass(0))
            else
                game.tryPerformAction(PlayCard(1, game.players[1].hand.first()))
        }
        assert(limit > 0)
    }
}