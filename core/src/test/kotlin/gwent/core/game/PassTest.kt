package gwent.core.game

import kotlin.test.Test

class PassTest {
    @Test
    fun pass01() {
        // Test that it is possible to pass immediately (starting-player agnostic)
        val game = Game("Alice", "Bob")
        game.tryPerformAction(Pass(game.currentPlayer))
    }
}