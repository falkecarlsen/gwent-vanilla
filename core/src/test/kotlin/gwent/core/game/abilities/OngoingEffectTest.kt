package gwent.core.game.abilities

import gwent.core.game.CardType
import gwent.core.game.CardType.*
import gwent.core.game.Game
import gwent.core.game.Pass
import gwent.core.game.PlayCard
import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals

class OngoingEffectTest {

    @Test
    fun militia01() {
        // Test if militia effect works
        val deck = TestSetup.stackedDeck(listOf(C4, S4, D4), listOf(), listOf())
        val game = Game("Alice", "Bob", deck, 0)
        val c4 = game.cards.find { it.type == C4 }!!
        val s4 = game.cards.find { it.type == S4 }!!
        val d4 = game.cards.find { it.type == D4 }!!

        game.tryPerformAction(PlayCard(0, C4, null))

        assertEquals(4, c4.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, S4, null))

        assertEquals(8, c4.currentPower)
        assertEquals(8, s4.currentPower)

        game.tryPerformAction(PlayCard(0, D4, null))

        assertEquals(12, c4.currentPower)
        assertEquals(12, s4.currentPower)
        assertEquals(12, d4.currentPower)
    }

    @Test
    fun captain01() {
        // Test if captain effect works
        val deck = TestSetup.stackedDeck(listOf(SJ, S5, S7), listOf(), listOf())
        val game = Game("Alice", "Bob", deck, 0)
        val sj = game.cards.find { it.type == SJ }!!

        game.tryPerformAction(PlayCard(0, SJ, null))

        assertEquals(3, sj.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, S5, null))

        assertEquals(6, sj.currentPower)

        game.tryPerformAction(PlayCard(0, S7, null))

        assertEquals(9, sj.currentPower)
    }

    @Test
    fun cavalry01() {
        // Test if cavalry effect works
        val initDeck = TestSetup.stackedDeck(listOf(S7, S4), listOf(), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)
        val s7 = game.cards.find { it.type == S7 }!!

        game.tryPerformAction(PlayCard(0, S7, null))

        assertEquals(9, s7.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, S4, null))

        assertEquals(7, s7.currentPower)
    }
}