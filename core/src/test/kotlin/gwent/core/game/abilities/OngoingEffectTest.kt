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
        val deck = TestSetup.stackedDeck(listOf(Card.Clubs4, Card.Spades4, Card.Diamond4), listOf(), listOf())
        val game = Game("Alice", "Bob", deck, 0)

        game.tryPerformAction(PlayCard(0, Card.Clubs4, null))

        assertEquals(4, Card.Clubs4.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, Card.Spades4, null))

        assertEquals(8, Card.Clubs4.currentPower)
        assertEquals(8, Card.Spades4.currentPower)

        game.tryPerformAction(PlayCard(0, Card.Diamond4, null))

        assertEquals(12, Card.Clubs4.currentPower)
        assertEquals(12, Card.Spades4.currentPower)
        assertEquals(12, Card.Diamond4.currentPower)
    }

    @Test
    fun captain01() {
        // Test if captain effect works
        val deck = TestSetup.stackedDeck(listOf(Card.SpadesJack, Card.Spades5, Card.Spades7), listOf(), listOf())
        val game = Game("Alice", "Bob", deck, 0)

        game.tryPerformAction(PlayCard(0, Card.SpadesJack, null))

        assertEquals(3, Card.SpadesJack.currentPower)

        game.tryPerformAction(Pass(1))
        game.tryPerformAction(PlayCard(0, Card.Spades5, null))

        assertEquals(6, Card.SpadesJack.currentPower)

        game.tryPerformAction(PlayCard(0, Card.Spades7, null))

        assertEquals(9, Card.SpadesJack.currentPower)
    }
}