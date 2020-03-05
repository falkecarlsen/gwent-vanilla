package gwent.vanilla.action

import com.beust.klaxon.*
import kotlin.reflect.KClass

/**
 * Class for capturing the polymorphism of [Action]s in serialized format.
 * Typical use will look like this
 * ```
 * val action: Action = Klaxon().parse<JsonAction>(json)!!.action
 * ```
 * See [Klaxon polymorphism](https://github.com/cbeust/klaxon#polymorphism)
 */
class JsonAction(
        @TypeFor(field = "action", adapter = ActionAdapter::class)
        val type: String,
        val action: Action
)

/**
 * TypeAdapter for handling the polymorphism of [Action]s.
 * See [Klaxon polymorphism](https://github.com/cbeust/klaxon#polymorphism)
 */
class ActionAdapter : TypeAdapter<Action> {
    override fun classFor(type: Any): KClass<out Action> = when (type as String) {
        "mulligan" -> Mulligan::class
        "mind_discard" -> MindDiscard::class
        "play_card" -> PlayCard::class
        "pass" -> Pass::class
        else -> throw KlaxonException("Unknown action type: $type")
    }
}