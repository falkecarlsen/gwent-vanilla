package gwent.vanilla.action

import kotlin.test.Test
import kotlin.test.assertEquals

class JsonActionParserTest {

    @Test
    fun test01() {
        val jsonActionParser = JsonActionParser()
        val action = jsonActionParser.parse("""
            {
                "action": "pass"
                "action_details": {
                    "player": 0
                }
            }
        """.trimIndent())
        val expected = PassData(0)
        assertEquals(expected, action)
    }
}