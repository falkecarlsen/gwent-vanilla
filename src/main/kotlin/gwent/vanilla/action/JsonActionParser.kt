package gwent.vanilla.action

import com.beust.klaxon.*
import kotlin.reflect.KClass

/**
 * Class for capturing the polymorphism of [ActionData] and [Action]s in serialized format.
 * Typical use will look like this
 * ```
 * val actionData: ActionData = Klaxon().parse<JsonActionData>(json)!!.data
 * ```
 * See [Klaxon polymorphism](https://github.com/cbeust/klaxon#polymorphism)
 */
class JsonActionData(
        @TypeFor(field = "data", adapter = ActionAdapter::class)
        val action: String,
        val data: ActionData
)

/**
 * TypeAdapter for handling the polymorphism of [ActionData] and [Action]s.
 * See [Klaxon polymorphism](https://github.com/cbeust/klaxon#polymorphism)
 */
class ActionAdapter : TypeAdapter<ActionData> {
    override fun classFor(type: Any): KClass<out ActionData> = when (type as String) {
        "mulligan" -> MulliganData::class
        "mind_discard" -> MindDiscardData::class
        "play_card" -> PlayCardData::class
        "pass" -> PassData::class
        else -> throw KlaxonException("Unknown action type: $type")
    }
}