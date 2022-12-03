package gwent.core.testing

import gwent.core.game.Card
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
}