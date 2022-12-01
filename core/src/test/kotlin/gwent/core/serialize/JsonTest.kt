package gwent.core.serialize

import com.beust.klaxon.Klaxon
import gwent.core.game.Game
import gwent.core.testing.TestSetup
import org.junit.Test
import kotlin.test.assertEquals

class JsonTest {
    @Test
    fun json01() {
        // Test if the json serialized returns the expected result
        val game = Game("Alice", "Bob", TestSetup.variedDeck(), 0)
        val json = Klaxon().toJsonString(game.toDTO())
        assertEquals(
            // Please inspect the produced json on updates
            "{\"currentPlayer\" : 0, \"deck\" : [{\"basePower\" : 12, \"name\" : \"DQ\", \"suit\" : \"diamonds\"}, {\"basePower\" : 8, \"name\" : \"D8\", \"suit\" : \"diamonds\"}, {\"basePower\" : 12, \"name\" : \"SQ\", \"suit\" : \"spades\"}, {\"basePower\" : 9, \"name\" : \"C9\", \"suit\" : \"clubs\"}, {\"basePower\" : 13, \"name\" : \"DK\", \"suit\" : \"diamonds\"}, {\"basePower\" : 13, \"name\" : \"CK\", \"suit\" : \"clubs\"}, {\"basePower\" : 7, \"name\" : \"S7\", \"suit\" : \"spades\"}, {\"basePower\" : 10, \"name\" : \"C10\", \"suit\" : \"clubs\"}, {\"basePower\" : 5, \"name\" : \"S5\", \"suit\" : \"spades\"}, {\"basePower\" : 11, \"name\" : \"SJ\", \"suit\" : \"spades\"}, {\"basePower\" : 6, \"name\" : \"C6\", \"suit\" : \"clubs\"}, {\"basePower\" : 3, \"name\" : \"D3\", \"suit\" : \"diamonds\"}, {\"basePower\" : 4, \"name\" : \"C4\", \"suit\" : \"clubs\"}], \"players\" : [{\"board\" : {\"clubs\" : {\"currentPower\" : 0, \"units\" : []}, \"currentPower\" : 0, \"diamonds\" : {\"currentPower\" : 0, \"units\" : []}, \"spades\" : {\"currentPower\" : 0, \"units\" : []}}, \"hand\" : [{\"basePower\" : 3, \"name\" : \"S3\", \"suit\" : \"spades\"}, {\"basePower\" : 6, \"name\" : \"D6\", \"suit\" : \"diamonds\"}, {\"basePower\" : 4, \"name\" : \"D4\", \"suit\" : \"diamonds\"}, {\"basePower\" : 7, \"name\" : \"C7\", \"suit\" : \"clubs\"}, {\"basePower\" : 11, \"name\" : \"CJ\", \"suit\" : \"clubs\"}, {\"basePower\" : 9, \"name\" : \"D9\", \"suit\" : \"diamonds\"}, {\"basePower\" : 13, \"name\" : \"SK\", \"suit\" : \"spades\"}, {\"basePower\" : 5, \"name\" : \"D5\", \"suit\" : \"diamonds\"}, {\"basePower\" : 12, \"name\" : \"CQ\", \"suit\" : \"clubs\"}, {\"basePower\" : 4, \"name\" : \"S4\", \"suit\" : \"spades\"}], \"hasPassed\" : false, \"index\" : 0, \"name\" : \"Alice\", \"roundsWon\" : 0}, {\"board\" : {\"clubs\" : {\"currentPower\" : 0, \"units\" : []}, \"currentPower\" : 0, \"diamonds\" : {\"currentPower\" : 0, \"units\" : []}, \"spades\" : {\"currentPower\" : 0, \"units\" : []}}, \"hand\" : [{\"basePower\" : 10, \"name\" : \"CS0\", \"suit\" : \"spades\"}, {\"basePower\" : 8, \"name\" : \"S8\", \"suit\" : \"spades\"}, {\"basePower\" : 6, \"name\" : \"S6\", \"suit\" : \"spades\"}, {\"basePower\" : 11, \"name\" : \"DJ\", \"suit\" : \"diamonds\"}, {\"basePower\" : 8, \"name\" : \"C8\", \"suit\" : \"clubs\"}, {\"basePower\" : 9, \"name\" : \"S9\", \"suit\" : \"spades\"}, {\"basePower\" : 7, \"name\" : \"D7\", \"suit\" : \"diamonds\"}, {\"basePower\" : 5, \"name\" : \"C5\", \"suit\" : \"clubs\"}, {\"basePower\" : 3, \"name\" : \"C3\", \"suit\" : \"clubs\"}, {\"basePower\" : 10, \"name\" : \"CD0\", \"suit\" : \"diamonds\"}], \"hasPassed\" : false, \"index\" : 1, \"name\" : \"Bob\", \"roundsWon\" : 0}], \"round\" : 0}",
            json
        )
    }
}