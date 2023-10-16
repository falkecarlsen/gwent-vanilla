package gwent.core.testing

import gwent.core.game.CardType
import gwent.core.game.CardType.*
import gwent.core.game.Game
import gwent.core.game.INIT_HAND_SIZE
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSetupTest {
    @Test
    fun stackedDeck01() {
        // Test if the deck is built as expected
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(S6, D5),
            p1Cards = listOf(CQ, D8),
            topOfDeck = listOf(S10, C3),
        ).map { it.type }

        assertEquals(CardType.entries.size, deck.size)
        assertEquals(deck[0], S6)
        assertEquals(deck[1], D5)
        assertEquals(deck[INIT_HAND_SIZE + 0], CQ)
        assertEquals(deck[INIT_HAND_SIZE + 1], D8)
        assertEquals(deck[2 * INIT_HAND_SIZE + 0], S10)
        assertEquals(deck[2 * INIT_HAND_SIZE + 1], C3)
    }

    @Test
    fun stackedDeck02() {
        // Test if the cards are dealt to the players and deck as expected
        val deck = TestSetup.stackedDeck(
            p0Cards = listOf(S7, D6),
            p1Cards = listOf(CJ, D9),
            topOfDeck = listOf(SJ, C4),
        )

        assertEquals(CardType.entries.size, deck.size)

        val game = Game("Alice", "Bob", deck)

        assertEquals(game.players[0].hand[0].type, S7)
        assertEquals(game.players[0].hand[1].type, D6)
        assertEquals(game.players[1].hand[0].type, CJ)
        assertEquals(game.players[1].hand[1].type, D9)
        assertEquals(game.deck[0].type, SJ)
        assertEquals(game.deck[1].type, C4)
    }

    @Test
    fun stackedDeck03() {
        // Test if the stacked deck contains all cards of a normal deck
        val testDeck = TestSetup.stackedDeck(
            p0Cards = listOf(S6, D5),
            p1Cards = listOf(CQ, D8),
            topOfDeck = listOf(S10, C3),
        ).map { it.type }.sortedBy { it.name }
        val realDeck = CardType.entries.sortedBy { it.name }

        assertEquals(CardType.entries.size, testDeck.size)
        assertEquals(realDeck, testDeck)
    }
}