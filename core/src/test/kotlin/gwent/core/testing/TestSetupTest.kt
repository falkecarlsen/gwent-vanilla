package gwent.core.testing

import gwent.core.game.Card
import gwent.core.game.Game
import gwent.core.game.INIT_HAND_SIZE
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSetupTest {
    @Test
    fun variedDeck01() {
        val testDeck = TestSetup.variedDeck().sortedBy { it.name }
        val realDeck = Card.all().sortedBy { it.name }

        assertEquals(Card.all().size, testDeck.size)
        assertEquals(realDeck, testDeck)
    }

    @Test
    fun stackedDeck01() {
        // Test if the deck is built as expected
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(Card.Spades6, Card.Diamond5),
            p1Cards = listOf(Card.ClubsQueen, Card.Diamond8),
            topOfDeck = listOf(Card.Spades10, Card.Clubs3),
        )

        assertEquals(Card.all().size, deck.size)
        assertEquals(deck[0], Card.Spades6)
        assertEquals(deck[1], Card.Diamond5)
        assertEquals(deck[INIT_HAND_SIZE + 0], Card.ClubsQueen)
        assertEquals(deck[INIT_HAND_SIZE + 1], Card.Diamond8)
        assertEquals(deck[2 * INIT_HAND_SIZE + 0], Card.Spades10)
        assertEquals(deck[2 * INIT_HAND_SIZE + 1], Card.Clubs3)
    }

    @Test
    fun stackedDeck02() {
        // Test if the cards are dealt to the players and deck as expected
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(Card.Spades7, Card.Diamond6),
            p1Cards = listOf(Card.ClubsJack, Card.Diamond9),
            topOfDeck = listOf(Card.SpadesJack, Card.Clubs4),
        )

        val game = Game("Alice", "Bob", deck.toMutableList())

        assertEquals(Card.all().size, deck.size)
        assertEquals(game.players[0].hand[0], Card.Spades7)
        assertEquals(game.players[0].hand[1], Card.Diamond6)
        assertEquals(game.players[1].hand[0], Card.ClubsJack)
        assertEquals(game.players[1].hand[1], Card.Diamond9)
        assertEquals(game.deck[0], Card.SpadesJack)
        assertEquals(game.deck[1], Card.Clubs4)
    }
}