package gwent.core.serialize

import gwent.core.game.Card
import gwent.core.game.CardType
import gwent.core.game.Game
import gwent.core.testing.TestSetup
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class JsonTest {
    @Test
    fun game01() {
        // Test if the json serialization returns the expected result
        val game = Game("Alice", "Bob", CardType.entries.sortedBy { it.name.hashCode() }.map { Card(it) }.toMutableList(), 0)
        val json = gwentKlaxonSetup().toJsonString(game.toDTO())
        assertEquals(
            // Please inspect the produced json on updates
            "{\"current_player\" : 0, \"deck\" : [{\"current_power\" : 3, \"type\" : \"H3\"}, {\"current_power\" : 0, \"type\" : \"H4\"}, {\"current_power\" : 5, \"type\" : \"H5\"}, {\"current_power\" : 6, \"type\" : \"H6\"}, {\"current_power\" : 7, \"type\" : \"H7\"}, {\"current_power\" : 8, \"type\" : \"H8\"}, {\"current_power\" : 9, \"type\" : \"H9\"}, {\"current_power\" : 0, \"type\" : \"HJ\"}, {\"current_power\" : 10, \"type\" : \"HK\"}, {\"current_power\" : 10, \"type\" : \"HQ\"}, {\"current_power\" : 3, \"type\" : \"S3\"}, {\"current_power\" : 0, \"type\" : \"S4\"}, {\"current_power\" : 5, \"type\" : \"S5\"}, {\"current_power\" : 6, \"type\" : \"S6\"}, {\"current_power\" : 7, \"type\" : \"S7\"}, {\"current_power\" : 8, \"type\" : \"S8\"}, {\"current_power\" : 9, \"type\" : \"S9\"}, {\"current_power\" : 0, \"type\" : \"SJ\"}, {\"current_power\" : 10, \"type\" : \"SK\"}, {\"current_power\" : 10, \"type\" : \"SQ\"}, {\"current_power\" : 10, \"type\" : \"C10\"}, {\"current_power\" : 10, \"type\" : \"D10\"}, {\"current_power\" : 10, \"type\" : \"H10\"}, {\"current_power\" : 10, \"type\" : \"S10\"}], \"players\" : [{\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"current_power\" : 3, \"type\" : \"C3\"}, {\"current_power\" : 0, \"type\" : \"C4\"}, {\"current_power\" : 5, \"type\" : \"C5\"}, {\"current_power\" : 6, \"type\" : \"C6\"}, {\"current_power\" : 7, \"type\" : \"C7\"}, {\"current_power\" : 8, \"type\" : \"C8\"}, {\"current_power\" : 9, \"type\" : \"C9\"}, {\"current_power\" : 0, \"type\" : \"CJ\"}, {\"current_power\" : 10, \"type\" : \"CK\"}, {\"current_power\" : 10, \"type\" : \"CQ\"}], \"has_passed\" : false, \"index\" : 0, \"name\" : \"Alice\", \"rounds_won\" : 0}, {\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"current_power\" : 3, \"type\" : \"D3\"}, {\"current_power\" : 0, \"type\" : \"D4\"}, {\"current_power\" : 5, \"type\" : \"D5\"}, {\"current_power\" : 6, \"type\" : \"D6\"}, {\"current_power\" : 7, \"type\" : \"D7\"}, {\"current_power\" : 8, \"type\" : \"D8\"}, {\"current_power\" : 9, \"type\" : \"D9\"}, {\"current_power\" : 0, \"type\" : \"DJ\"}, {\"current_power\" : 10, \"type\" : \"DK\"}, {\"current_power\" : 10, \"type\" : \"DQ\"}], \"has_passed\" : false, \"index\" : 1, \"name\" : \"Bob\", \"rounds_won\" : 0}], \"round\" : 0}",
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
        assertEquals(CardType.SK.name, action.cardName)
    }
}