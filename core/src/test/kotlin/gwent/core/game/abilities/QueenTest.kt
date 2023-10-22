package gwent.core.game.abilities

import gwent.core.game.*
import gwent.core.testing.TestSetup
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class QueenTest {

    @Test
    fun queen01() {
        // Test if there can only be one queen
        val initDeck = TestSetup.stackedDeck(listOf(CardType.DQ, CardType.CQ), listOf(CardType.SQ), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        game.tryPerformAction(PlayCard(0, CardType.DQ, null))

        assertThrows(ExistingQueenException::class.java) { game.tryPerformAction(PlayCard(1, CardType.SQ, null)) }
        game.tryPerformAction(Pass(1))

        assertThrows(ExistingQueenException::class.java) { game.tryPerformAction(PlayCard(0, CardType.CQ, null)) }
    }
}