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
            "{\"current_player\" : 0, \"deck\" : [{\"current_power\" : 3, \"name\" : \"H3\"}, {\"current_power\" : 0, \"name\" : \"H4\"}, {\"current_power\" : 5, \"name\" : \"H5\"}, {\"current_power\" : 6, \"name\" : \"H6\"}, {\"current_power\" : 7, \"name\" : \"H7\"}, {\"current_power\" : 8, \"name\" : \"H8\"}, {\"current_power\" : 9, \"name\" : \"H9\"}, {\"current_power\" : 0, \"name\" : \"HJ\"}, {\"current_power\" : 10, \"name\" : \"HK\"}, {\"current_power\" : 10, \"name\" : \"HQ\"}, {\"current_power\" : 3, \"name\" : \"S3\"}, {\"current_power\" : 0, \"name\" : \"S4\"}, {\"current_power\" : 5, \"name\" : \"S5\"}, {\"current_power\" : 6, \"name\" : \"S6\"}, {\"current_power\" : 7, \"name\" : \"S7\"}, {\"current_power\" : 8, \"name\" : \"S8\"}, {\"current_power\" : 9, \"name\" : \"S9\"}, {\"current_power\" : 0, \"name\" : \"SJ\"}, {\"current_power\" : 10, \"name\" : \"SK\"}, {\"current_power\" : 10, \"name\" : \"SQ\"}, {\"current_power\" : 10, \"name\" : \"C10\"}, {\"current_power\" : 10, \"name\" : \"D10\"}, {\"current_power\" : 10, \"name\" : \"H10\"}, {\"current_power\" : 10, \"name\" : \"S10\"}], \"players\" : [{\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"current_power\" : 3, \"name\" : \"C3\"}, {\"current_power\" : 0, \"name\" : \"C4\"}, {\"current_power\" : 5, \"name\" : \"C5\"}, {\"current_power\" : 6, \"name\" : \"C6\"}, {\"current_power\" : 7, \"name\" : \"C7\"}, {\"current_power\" : 8, \"name\" : \"C8\"}, {\"current_power\" : 9, \"name\" : \"C9\"}, {\"current_power\" : 0, \"name\" : \"CJ\"}, {\"current_power\" : 10, \"name\" : \"CK\"}, {\"current_power\" : 10, \"name\" : \"CQ\"}], \"has_passed\" : false, \"index\" : 0, \"name\" : \"Alice\", \"rounds_won\" : 0}, {\"board\" : {\"clubs\" : {\"current_power\" : 0, \"units\" : []}, \"current_power\" : 0, \"diamonds\" : {\"current_power\" : 0, \"units\" : []}, \"spades\" : {\"current_power\" : 0, \"units\" : []}}, \"hand\" : [{\"current_power\" : 3, \"name\" : \"D3\"}, {\"current_power\" : 0, \"name\" : \"D4\"}, {\"current_power\" : 5, \"name\" : \"D5\"}, {\"current_power\" : 6, \"name\" : \"D6\"}, {\"current_power\" : 7, \"name\" : \"D7\"}, {\"current_power\" : 8, \"name\" : \"D8\"}, {\"current_power\" : 9, \"name\" : \"D9\"}, {\"current_power\" : 0, \"name\" : \"DJ\"}, {\"current_power\" : 10, \"name\" : \"DK\"}, {\"current_power\" : 10, \"name\" : \"DQ\"}], \"has_passed\" : false, \"index\" : 1, \"name\" : \"Bob\", \"rounds_won\" : 0}], \"round\" : 0}",
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