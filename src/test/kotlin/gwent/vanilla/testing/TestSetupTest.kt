package gwent.vanilla.testing

import gwent.vanilla.domain.Card
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSetupTest {
    @Test
    fun variedDeck01() {
        val testDeck = TestSetup.variedDeck().sortedBy { it.id }
        val realDeck = Card.all().sortedBy { it.id }

        assertEquals(Card.all().size, testDeck.size)
        assertEquals(realDeck, testDeck)
    }
}