package gwent.vanilla.action

import com.beust.klaxon.Klaxon
import gwent.vanilla.domain.Alignment
import gwent.vanilla.domain.RowSuit
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonActionParserTest {

    private fun parseJsonAction(json: String): ActionData = Klaxon().parse<JsonActionData>(json)!!.data

    @Test
    fun pass01() {
        val action = parseJsonAction("""
            {
                "action": "pass",
                "data": {
                    "player": 0
                }
            }
        """.trimIndent())
        val expected = PassData(0)
        assertEquals(expected, action)
    }

    @Test
    fun mulligan01() {
        val action = parseJsonAction("""
            {
                "action": "mulligan",
                "data": {
                    "player": 0,
                    "alignment": "Mind",
                    "discardedCards": [ 3, 8 ]
                }
            }
        """.trimIndent())
        val expected = MulliganData(0, listOf(3, 8), Alignment.Mind)
        assertEquals(expected, action)
    }

    @Test
    fun mulligan02() {
        val action = parseJsonAction("""
            {
                "action": "mulligan",
                "data": {
                    "player": 1,
                    "alignment": "Might",
                    "discardedCards": [ 0, 1, 2, 100 ]
                }
            }
        """.trimIndent())
        val expected = MulliganData(1, listOf(0, 1, 2, 100), Alignment.Might)
        assertEquals(expected, action)
    }

    @Test
    fun mindDiscard01() {
        val action = parseJsonAction("""
            {
                "action": "mind_discard",
                "data": {
                    "player": 1,
                    "discardedCard": 5
                }
            }
        """.trimIndent())
        val expected = MindDiscardData(1, 5)
        assertEquals(expected, action)
    }

    @Test
    fun playCard01() {
        val action = parseJsonAction("""
            {
                "action": "play_card",
                "data": {
                    "player": 0,
                    "card": 11,
                    "row": "DIAMONDS",
                    "target": 4
                }
            }
        """.trimIndent())
        val expected = PlayCardData(0, 11, RowSuit.DIAMONDS, 4)
        assertEquals(expected, action)
    }

    @Test
    fun playCard02() {
        val action = parseJsonAction("""
            {
                "action": "play_card",
                "data": {
                    "player": 1,
                    "card": 0
                }
            }
        """.trimIndent())
        val expected = PlayCardData(1, 0)
        assertEquals(expected, action)
    }
}