package gwent.vanilla.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {
    @Test
    fun setup01() {
        // Test if initial card dealing is correct
        val game = Game("Alice", "Bob")
        assertEquals(game.deck.size, CARDS - 2 * INIT_HAND_SIZE)
        assertEquals(game.players[0].hand.size, INIT_HAND_SIZE)
        assertEquals(game.players[1].hand.size, INIT_HAND_SIZE)
    }
}