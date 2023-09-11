package gwent.core.game.abilities

import gwent.core.game.Card
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
        val initDeck = TestSetup.stackedDeck(listOf(Card.Clubs4, Card.Spades4, Card.Diamond4), listOf(), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        game.tryPerformAction(PlayCard(0, Card.Clubs4))

        assertEquals(4, Card.Clubs4.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, Card.Spades4))

        assertEquals(8, Card.Clubs4.currentPower)
        assertEquals(8, Card.Spades4.currentPower)

        game.tryPerformAction(PlayCard(0, Card.Diamond4))

        assertEquals(12, Card.Clubs4.currentPower)
        assertEquals(12, Card.Spades4.currentPower)
        assertEquals(12, Card.Diamond4.currentPower)
    }

    @Test
    fun captain01() {
        // Test if captain effect works
        val initDeck = TestSetup.stackedDeck(listOf(Card.SpadesJack, Card.Spades5, Card.Spades7), listOf(), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        game.tryPerformAction(PlayCard(0, Card.SpadesJack))

        assertEquals(3, Card.SpadesJack.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, Card.Spades5))

        assertEquals(6, Card.SpadesJack.currentPower)

        game.tryPerformAction(PlayCard(0, Card.Spades7))

        assertEquals(9, Card.SpadesJack.currentPower)
    }

    @Test
    fun cavalry01() {
        // Test if cavalry effect works
        val initDeck = TestSetup.stackedDeck(listOf(Card.Spades7, Card.Spades4), listOf(), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        game.tryPerformAction(PlayCard(0, Card.Spades7))

        assertEquals(9, Card.Spades7.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, Card.Spades5))

        assertEquals(7, Card.Spades7.currentPower)
    }
}