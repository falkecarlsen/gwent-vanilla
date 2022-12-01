package gwent.core.serialize

import com.beust.klaxon.FieldRenamer
import com.beust.klaxon.Klaxon
import gwent.core.game.Game
import gwent.core.testing.TestSetup
import org.junit.Test
import kotlin.test.assertEquals

class JsonTest {
    @Test
    fun json01() {
        // Test if the json serialization returns the expected result
        val game = Game("Alice", "Bob", TestSetup.variedDeck(), 0)
        val json = gwentKlaxonSetup().toJsonString(game.toDTO())
        assertEquals(
            // Please inspect the produced json on updates
            "{\"current_player\" : 0, \"deck\" : [{\"base_power\" : 12, \"name\" : \"DQ\", \"suit\" : \"diamonds\"}, {\"base_power\" : 8, \"name\" : \"D8\", \"suit\" : \"diamonds\"}, {\"base_power\" : 12, \"name\" : \"SQ\", \"suit\" : \"spades\"}, {\"base_power\" : 9, \"name\" : \"C9\", \"suit\" : \"clubs\"}, {\"base_power\" : 13, \"name\" : \"DK\", \"suit\" : \"diamonds\"}, {\"base_power\" : 13, \"name\" : \"CK\", \"suit\" : \"clubs\"}, {\"base_power\" : 7, \"name\" : \"S7\", \"suit\" : \"spades\"}, {\"base_power\" : 10, \"name\" : \"C10\", \"suit\" : \"clubs\"}, {\"base_power\" : 5, \"name\" : \"S5\", \"suit\" : \"spades\"}, {\"base_power\" : 11, \"name\" : \"SJ\", \"suit\" : \"spades\"}, {\"base_power\" : 6, \"name\" : \"C6\", \"suit\" : \"clubs\"}, {\"base_power\" : 3, \"name\" : \"D3\", \"suit\" : \"diamonds\"}, {\"base_power\" : 4, \"name\" : \"C4\", \"suit\" : \"clubs\"}], \"players\" : [{\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"base_power\" : 3, \"name\" : \"S3\", \"suit\" : \"spades\"}, {\"base_power\" : 6, \"name\" : \"D6\", \"suit\" : \"diamonds\"}, {\"base_power\" : 4, \"name\" : \"D4\", \"suit\" : \"diamonds\"}, {\"base_power\" : 7, \"name\" : \"C7\", \"suit\" : \"clubs\"}, {\"base_power\" : 11, \"name\" : \"CJ\", \"suit\" : \"clubs\"}, {\"base_power\" : 9, \"name\" : \"D9\", \"suit\" : \"diamonds\"}, {\"base_power\" : 13, \"name\" : \"SK\", \"suit\" : \"spades\"}, {\"base_power\" : 5, \"name\" : \"D5\", \"suit\" : \"diamonds\"}, {\"base_power\" : 12, \"name\" : \"CQ\", \"suit\" : \"clubs\"}, {\"base_power\" : 4, \"name\" : \"S4\", \"suit\" : \"spades\"}], \"has_passed\" : false, \"index\" : 0, \"name\" : \"Alice\", \"rounds_won\" : 0}, {\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"base_power\" : 10, \"name\" : \"S10\", \"suit\" : \"spades\"}, {\"base_power\" : 8, \"name\" : \"S8\", \"suit\" : \"spades\"}, {\"base_power\" : 6, \"name\" : \"S6\", \"suit\" : \"spades\"}, {\"base_power\" : 11, \"name\" : \"DJ\", \"suit\" : \"diamonds\"}, {\"base_power\" : 8, \"name\" : \"C8\", \"suit\" : \"clubs\"}, {\"base_power\" : 9, \"name\" : \"S9\", \"suit\" : \"spades\"}, {\"base_power\" : 7, \"name\" : \"D7\", \"suit\" : \"diamonds\"}, {\"base_power\" : 5, \"name\" : \"C5\", \"suit\" : \"clubs\"}, {\"base_power\" : 3, \"name\" : \"C3\", \"suit\" : \"clubs\"}, {\"base_power\" : 10, \"name\" : \"D10\", \"suit\" : \"diamonds\"}], \"has_passed\" : false, \"index\" : 1, \"name\" : \"Bob\", \"rounds_won\" : 0}], \"round\" : 0}",
            json
        )
    }
}