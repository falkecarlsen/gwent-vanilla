package gwent.core.serialize

import gwent.core.game.Card
import gwent.core.game.Game
import gwent.core.testing.TestSetup
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class JsonTest {
    @Test
    fun game01() {
        // Test if the json serialization returns the expected result
        val game = Game("Alice", "Bob", Card.all().sortedBy { it.name.hashCode() }.toMutableList(), 0)
        val json = gwentKlaxonSetup().toJsonString(game.toDTO())
        assertEquals(
            // Please inspect the produced json on updates
            "{\"current_player\" : 0, \"deck\" : [{\"base_power\" : 3, \"current_power\" : 3, \"name\" : \"H3\", \"numeric\" : 3, \"suit\" : \"hearts\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"H4\", \"numeric\" : 4, \"suit\" : \"hearts\"}, {\"base_power\" : 5, \"current_power\" : 5, \"name\" : \"H5\", \"numeric\" : 5, \"suit\" : \"hearts\"}, {\"base_power\" : 6, \"current_power\" : 6, \"name\" : \"H6\", \"numeric\" : 6, \"suit\" : \"hearts\"}, {\"base_power\" : 7, \"current_power\" : 7, \"name\" : \"H7\", \"numeric\" : 7, \"suit\" : \"hearts\"}, {\"base_power\" : 8, \"current_power\" : 8, \"name\" : \"H8\", \"numeric\" : 8, \"suit\" : \"hearts\"}, {\"base_power\" : 9, \"current_power\" : 9, \"name\" : \"H9\", \"numeric\" : 9, \"suit\" : \"hearts\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"HJ\", \"numeric\" : 11, \"suit\" : \"hearts\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"HK\", \"numeric\" : 13, \"suit\" : \"hearts\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"HQ\", \"numeric\" : 12, \"suit\" : \"hearts\"}, {\"base_power\" : 3, \"current_power\" : 3, \"name\" : \"S3\", \"numeric\" : 3, \"suit\" : \"spades\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"S4\", \"numeric\" : 4, \"suit\" : \"spades\"}, {\"base_power\" : 5, \"current_power\" : 5, \"name\" : \"S5\", \"numeric\" : 5, \"suit\" : \"spades\"}, {\"base_power\" : 6, \"current_power\" : 6, \"name\" : \"S6\", \"numeric\" : 6, \"suit\" : \"spades\"}, {\"base_power\" : 7, \"current_power\" : 7, \"name\" : \"S7\", \"numeric\" : 7, \"suit\" : \"spades\"}, {\"base_power\" : 8, \"current_power\" : 8, \"name\" : \"S8\", \"numeric\" : 8, \"suit\" : \"spades\"}, {\"base_power\" : 9, \"current_power\" : 9, \"name\" : \"S9\", \"numeric\" : 9, \"suit\" : \"spades\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"SJ\", \"numeric\" : 11, \"suit\" : \"spades\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"SK\", \"numeric\" : 13, \"suit\" : \"spades\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"SQ\", \"numeric\" : 12, \"suit\" : \"spades\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"C10\", \"numeric\" : 10, \"suit\" : \"clubs\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"D10\", \"numeric\" : 10, \"suit\" : \"diamonds\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"H10\", \"numeric\" : 10, \"suit\" : \"hearts\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"S10\", \"numeric\" : 10, \"suit\" : \"spades\"}], \"players\" : [{\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"base_power\" : 3, \"current_power\" : 3, \"name\" : \"C3\", \"numeric\" : 3, \"suit\" : \"clubs\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"C4\", \"numeric\" : 4, \"suit\" : \"clubs\"}, {\"base_power\" : 5, \"current_power\" : 5, \"name\" : \"C5\", \"numeric\" : 5, \"suit\" : \"clubs\"}, {\"base_power\" : 6, \"current_power\" : 6, \"name\" : \"C6\", \"numeric\" : 6, \"suit\" : \"clubs\"}, {\"base_power\" : 7, \"current_power\" : 7, \"name\" : \"C7\", \"numeric\" : 7, \"suit\" : \"clubs\"}, {\"base_power\" : 8, \"current_power\" : 8, \"name\" : \"C8\", \"numeric\" : 8, \"suit\" : \"clubs\"}, {\"base_power\" : 9, \"current_power\" : 9, \"name\" : \"C9\", \"numeric\" : 9, \"suit\" : \"clubs\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"CJ\", \"numeric\" : 11, \"suit\" : \"clubs\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"CK\", \"numeric\" : 13, \"suit\" : \"clubs\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"CQ\", \"numeric\" : 12, \"suit\" : \"clubs\"}], \"has_passed\" : false, \"index\" : 0, \"name\" : \"Alice\", \"rounds_won\" : 0}, {\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"base_power\" : 3, \"current_power\" : 3, \"name\" : \"D3\", \"numeric\" : 3, \"suit\" : \"diamonds\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"D4\", \"numeric\" : 4, \"suit\" : \"diamonds\"}, {\"base_power\" : 5, \"current_power\" : 5, \"name\" : \"D5\", \"numeric\" : 5, \"suit\" : \"diamonds\"}, {\"base_power\" : 6, \"current_power\" : 6, \"name\" : \"D6\", \"numeric\" : 6, \"suit\" : \"diamonds\"}, {\"base_power\" : 7, \"current_power\" : 7, \"name\" : \"D7\", \"numeric\" : 7, \"suit\" : \"diamonds\"}, {\"base_power\" : 8, \"current_power\" : 8, \"name\" : \"D8\", \"numeric\" : 8, \"suit\" : \"diamonds\"}, {\"base_power\" : 9, \"current_power\" : 9, \"name\" : \"D9\", \"numeric\" : 9, \"suit\" : \"diamonds\"}, {\"base_power\" : 0, \"current_power\" : 0, \"name\" : \"DJ\", \"numeric\" : 11, \"suit\" : \"diamonds\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"DK\", \"numeric\" : 13, \"suit\" : \"diamonds\"}, {\"base_power\" : 10, \"current_power\" : 10, \"name\" : \"DQ\", \"numeric\" : 12, \"suit\" : \"diamonds\"}], \"has_passed\" : false, \"index\" : 1, \"name\" : \"Bob\", \"rounds_won\" : 0}], \"round\" : 0}",
            json
        )
    }

    @Test
    fun action01() {
        // Test if the Pass action is parsed correctly
        val json = "{\"type\":\"${ActionDTO.PASS_TYPE}\",\"player\":0}"
        val action = gwentKlaxonSetup().parse<ActionDTO>(json)
        assertIs<PassDTO>(action)
        assertEquals(0, action.player)
    }

    @Test
    fun action02() {
        // Test if the PlayCard action is parsed correctly
        val json = "{\"type\":\"${ActionDTO.PLAY_CARD_TYPE}\",\"player\":1,\"card_name\":\"SK\",\"row\":\"null\"}"
        val action = gwentKlaxonSetup().parse<ActionDTO>(json)
        assertIs<PlayCardDTO>(action)
        assertEquals(1, action.player)
        assertEquals(Card.SpadesKing.name, action.cardName)
    }
}