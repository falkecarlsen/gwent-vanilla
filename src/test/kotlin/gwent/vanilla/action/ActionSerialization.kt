package gwent.vanilla.action

import com.beust.klaxon.Klaxon
import gwent.vanilla.domain.Alignment
import gwent.vanilla.domain.RowSuit
import kotlin.test.Test
import kotlin.test.assertEquals

class ActionSerialization {

    private fun parseJsonAction(json: String): Action = Klaxon().parse<Action>(json)!!

    @Test
    fun pass01() {
        val action = parseJsonAction("""
            {
                "type": "Pass",
                "player": 0
            }
        """.trimIndent())
        val expected = Pass(0)
        assertEquals(expected, action)
    }

    @Test
    fun pass02() {
        val klaxon = Klaxon()
        val original = Pass(1)
        val parsed = klaxon.parse<Action>(klaxon.toJsonString(original))
        assertEquals(original, parsed)
    }

    @Test
    fun mulligan01() {
        val action = parseJsonAction("""
            {
                "type": "Mulligan",
                "player": 0,
                "alignment": "Mind",
                "discardedCards": [ 3, 8 ]
            }
        """.trimIndent())
        val expected = Mulligan(0, listOf(3, 8), Alignment.Mind)
        assertEquals(expected, action)
    }

    @Test
    fun mulligan02() {
        val action = parseJsonAction("""
            {
                "type": "Mulligan",
                "player": 1,
                "alignment": "Might",
                "discardedCards": [ 0, 1, 2, 100 ]
            }
        """.trimIndent())
        val expected = Mulligan(1, listOf(0, 1, 2, 100), Alignment.Might)
        assertEquals(expected, action)
    }

    @Test
    fun mulligan03() {
        val klaxon = Klaxon()
        val original = Mulligan(1, listOf(3, 2, -1), Alignment.Magic)
        val parsed = klaxon.parse<Action>(klaxon.toJsonString(original))
        assertEquals(original, parsed)
    }

    @Test
    fun mindDiscard01() {
        val action = parseJsonAction("""
            {
                "type": "MindDiscard",
                "player": 1,
                "discardedCard": 5
            }
        """.trimIndent())
        val expected = MindDiscard(1, 5)
        assertEquals(expected, action)
    }

    @Test
    fun mindDiscard02() {
        val klaxon = Klaxon()
        val original = MindDiscard(0, 30)
        val parsed = klaxon.parse<Action>(klaxon.toJsonString(original))
        assertEquals(original, parsed)
    }

    @Test
    fun playCard01() {
        val action = parseJsonAction("""
            {
                "type": "PlayCard",
                "player": 0,
                "card": 11,
                "row": "DIAMONDS",
                "target": 4
            }
        """.trimIndent())
        val expected = PlayCard(0, 11, RowSuit.DIAMONDS, 4)
        assertEquals(expected, action)
    }

    @Test
    fun playCard02() {
        val action = parseJsonAction("""
            {
                "type": "PlayCard",
                "player": 1,
                "card": 0
            }
        """.trimIndent())
        val expected = PlayCard(1, 0)
        assertEquals(expected, action)
    }

    @Test
    fun playCard03() {
        val klaxon = Klaxon()
        val original = PlayCard(0, 0, RowSuit.CLUBS)
        val parsed = klaxon.parse<Action>(klaxon.toJsonString(original))
        assertEquals(original, parsed)
    }
}