package gwent.core.game.abilities

import gwent.core.game.*
import gwent.core.game.CardType.*
import gwent.core.testing.TestSetup
import kotlin.test.Test
import kotlin.test.assertEquals

class SpiesTest {
    @Test
    fun spies01() {
        // Test if spies are placed on opponents board
        val initDeck = TestSetup.stackedDeck(listOf(C3), listOf(S3), listOf())
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)
        val c3 = game.cards.find { it.type == C3 }!!
        val s3 = game.cards.find { it.type == S3 }!!

        game.tryPerformAction(PlayCard(0, C3, null))

        assert(c3 in game.players[1].board.rows[RowSuit.CLUBS]!!.cards)
        assertEquals(3, game.players[1].board.currentPower)
        assertEquals(1, c3.owner)

        game.tryPerformAction(PlayCard(1, S3, null))

        assert(s3 in game.players[0].board.rows[RowSuit.SPADES]!!.cards)
        assertEquals(3, game.players[0].board.currentPower)
        assertEquals(0, s3.owner)
    }

    @Test
    fun spies02() {
        // Test if spies draws a card (for the correct player)
        val initDeck = TestSetup.stackedDeck(
            listOf(C3),
            listOf(S3),
            listOf(DQ, DK)
        )
        val game = Game("Alice", "Bob", initDeck.toMutableList(), 0)

        assertEquals(INIT_HAND_SIZE, game.players[0].hand.size)
        assertEquals(INIT_HAND_SIZE, game.players[1].hand.size)
        assertEquals(DQ, game.deck[0].type)
        assertEquals(DK, game.deck[1].type)

        game.tryPerformAction(PlayCard(0, C3, null))

        assertEquals(INIT_HAND_SIZE, game.players[0].hand.size)
        assert(game.players[0].hand.any { it.type == DQ })

        game.tryPerformAction(PlayCard(1, S3, null))

        assertEquals(INIT_HAND_SIZE, game.players[1].hand.size)
        assert(game.players[1].hand.any { it.type == DK })
    }
}